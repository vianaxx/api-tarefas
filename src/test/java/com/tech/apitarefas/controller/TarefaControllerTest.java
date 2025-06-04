package com.tech.apitarefas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.apitarefas.model.Tarefa;
import com.tech.apitarefas.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    private MockMvc mockMvc;
    private TarefaService service;

    private ObjectMapper objectMapper;

    private Tarefa tarefa;


    void setup() {
        tarefa = new Tarefa(1L, "Testar", "Controlador", false);
    }

    @Test
        // GET /tarefas
    void deveListarTodasAsTarefas() throws Exception {

        when(service.listarTodas()).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Testar"));
    }


    @Test
        //GET /tarefas/{id}
    void deveBuscarTarefaPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(tarefa));

        mockMvc.perform(get("/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Controlador"));
    }

    @Test
    void deveRetornar404SeTarefaNaoExistir() throws Exception {
        when(service.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tarefas/99"))
                .andExpect(status().isNotFound());
    }


    @Test
        // POST /tarefas
    void deveCriarNovaTarefa() throws Exception {
        Tarefa nova = new Tarefa(null, "Nova", "Desc", false);
        Tarefa salva = new Tarefa(1L, "Nova", "Desc", false);

        when(service.criarOuAtualizar(any())).thenReturn(salva);

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nova)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
        // PUT /tarefas/{id}
    void deveAtualizarTarefaExistente() throws Exception {
        Tarefa atualizada = new Tarefa(1L, "Atualizada", "Nova desc", true);
        when(service.buscarPorId(1L)).thenReturn(Optional.of(tarefa));
        when(service.criarOuAtualizar(any())).thenReturn(atualizada);

        mockMvc.perform(put("/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizada"));
    }


    @Test
        // DELETE /tarefas/{id}
    void deveDeletarTarefa() throws Exception {
        when(service.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/tarefas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoDeletarInexistente() throws Exception {
        when(service.deletar(99L)).thenReturn(false);

        mockMvc.perform(delete("/tarefas/99"))
                .andExpect(status().isNotFound());
    }


    @Test
        //Teste filtros extras (/tarefas/status, /tarefas/ordenada
    void deveListarPorStatus() throws Exception {
        when(service.listarPorStatus(true)).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/tarefas/status?concluida=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void deveListarOrdenadas() throws Exception {
        when(service.listarOrdenadasPorTitulo()).thenReturn(List.of(tarefa));

        mockMvc.perform(get("/tarefas/ordenada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Testar"));
    }


}
