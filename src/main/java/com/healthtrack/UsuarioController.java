package com.healthtrack;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final Map<Long, Usuario> usuarios = new ConcurrentHashMap<>();

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(System.currentTimeMillis());
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarios.get(id);
    }


    public static class PesoRequest {
        double peso;

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }
    }

    @PutMapping("/{id}/peso")
    public Usuario actualizarPeso(@PathVariable Long id, @RequestBody PesoRequest request) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null) {
            usuario.setPeso(request.getPeso());
        }
        return usuario;
    }
}
