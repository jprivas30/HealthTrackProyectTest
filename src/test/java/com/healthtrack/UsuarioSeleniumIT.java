package com.healthtrack;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioSeleniumIT {

    private WebDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1200,800");
        driver = new ChromeDriver(options);


        String jsonUsuario = "{\"id\":1,\"Jp\":\"Prueba\",\"peso\":70.0}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/usuarios"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonUsuario))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void testActualizarPeso() {
        driver.get("http://localhost:8080/index.html");

        WebElement idInput = driver.findElement(By.id("usuarioId"));
        WebElement pesoInput = driver.findElement(By.id("nuevoPeso"));
        WebElement form = driver.findElement(By.id("pesoForm"));

        idInput.sendKeys("1");
        pesoInput.sendKeys("85.5");
        form.submit();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("resultado"), "kg"));

        WebElement resultado = driver.findElement(By.id("resultado"));
        String texto = resultado.getText();

        System.out.println("Texto mostrado: " + texto);
        assertTrue(texto.contains("85.5"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

