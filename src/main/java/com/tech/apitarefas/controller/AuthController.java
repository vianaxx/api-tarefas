package com.tech.apitarefas.controller;

import com.tech.apitarefas.model.Usuario;
import com.tech.apitarefas.security.JwtUtil;
import com.tech.apitarefas.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario criado = authService.registrar(usuario);
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {
        try {
            String email = dados.get("email");
            String senha = dados.get("senha");

            Usuario usuario = authService.autenticar(email, senha);
            String token = jwtUtil.gerarToken(usuario.getEmail());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", usuario.getEmail(),
                    "nome", usuario.getNome()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
