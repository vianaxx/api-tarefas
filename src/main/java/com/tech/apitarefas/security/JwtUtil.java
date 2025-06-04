package com.tech.apitarefas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long TEMPO_EXPIRACAO = 86400000; // 1 dia em milissegundos

    // Gera um token JWT com o nome de usuário
    public String gerarToken(String nomeUsuario) {
        return Jwts.builder()
                .setSubject(nomeUsuario)
                .setIssuedAt(new Date()) // data de criação
                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_EXPIRACAO)) // data de expiração
                .signWith(chaveSecreta) // assina com a chave secreta
                .compact(); // compacta em string
    }

    // Extrai o nome do usuário de dentro do token
    public String extrairNomeUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chaveSecreta)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se o token é válido (nome de usuário e se não expirou)
    public boolean validarToken(String token, String nomeUsuario) {
        String nomeExtraido = extrairNomeUsuario(token);
        return nomeExtraido.equals(nomeUsuario) && !estaExpirado(token);
    }

    // Verifica se o token está expirado
    private boolean estaExpirado(String token) {
        Date dataExpiracao = Jwts.parserBuilder()
                .setSigningKey(chaveSecreta)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return dataExpiracao.before(new Date());
    }
}
