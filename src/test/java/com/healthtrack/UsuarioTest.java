
package com.healthtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testActualizarPeso() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setPeso(70.0);
        usuario.setPeso(75.5);
        assertEquals(75.5, usuario.getPeso(), 0.01);
    }
}
