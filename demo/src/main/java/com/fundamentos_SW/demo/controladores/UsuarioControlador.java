package com.fundamentos_SW.demo.controladores;

import com.fundamentos_SW.demo.model.Usuario;
import com.fundamentos_SW.demo.servicio.RegistroUsuarioHU7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private RegistroUsuarioHU7 registroService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            String resultado = registroService.registrarUsuario(usuario);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/registro", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsRegistro() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        if (usuario.getCorreo() == null || usuario.getContrasenia() == null) {
            return ResponseEntity.badRequest().body("Correo y contraseña son obligatorios");
        }
        return registroService.autenticarUsuario(usuario.getCorreo(), usuario.getContrasenia())
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(u))
                .orElseGet(() -> ResponseEntity.status(401).body("Usuario o contraseña incorrectos"));
    }
}

