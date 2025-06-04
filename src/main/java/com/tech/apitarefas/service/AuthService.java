package com.tech.apitarefas.service;


import com.tech.apitarefas.model.Usuario;
import com.tech.apitarefas.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = new BCryptPasswordEncoder();
    }


    public Usuario registrar(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.buscarPorEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.salvar(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha inválida.");
        }

        return usuario;
    }
}
