package com.tech.apitarefas.repository;

import com.tech.apitarefas.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsuarioRepository {

    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public Optional<Usuario> buscarPorEmail(String email){
        return Optional.ofNullable(usuarios.get(email));
    }

    public Usuario salvar(Usuario usuario){
        usuario.setId(contador.getAndIncrement());
        usuarios.put(usuario.getEmail(), usuario);
        return usuario;

    }

}
