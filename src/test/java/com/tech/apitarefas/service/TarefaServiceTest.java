package com.tech.apitarefas.service;

import com.tech.apitarefas.model.Tarefa;
import com.tech.apitarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TarefaServiceTest {
    private TarefaService service;
    private TarefaRepository repository;

    @BeforeEach
    void setup(){
        repository = mock(TarefaRepository.class);
        service = new TarefaService(repository);
    }

    // devolve todas as tarefas vindas do repositório
    @Test
    void deveListarTodasAsTarefas(){
        List<Tarefa> mockTarefas = Arrays.asList(
                new Tarefa(1L, "Estudar", "Java", false),
                new Tarefa(2L, "Ler", "Spring", true)
        );
        when(repository.listar()).thenReturn(mockTarefas);

        List<Tarefa>resultado = service.listarTodas();

        assertEquals(2, resultado.size());
        verify(repository, timeout(1)).listar();
    }

    @Test
    void deveBuscarTarefaPorId(){

        Tarefa tarefa = new Tarefa(1L, "Estudar", "Java", false);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(tarefa));

        Optional<Tarefa> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Estudar", resultado.get().getTitulo());
        verify(repository).buscarPorId(1L);
    }

    @Test
    void deveSalvarNovaTarefa(){
        Tarefa novaTarefa = new Tarefa(null, "Nova", "Tarefa", false);
        Tarefa salva = new Tarefa(1L, "Nova","Tarefa", false);

        when(repository.salvar(novaTarefa)).thenReturn(salva);

        Tarefa resultado = service.criarOuAtualizar(novaTarefa);

        assertNotNull(resultado.getId());
        assertEquals("Nova", resultado.getTitulo());
        verify(repository).salvar(novaTarefa);
    }

    @Test
    void deveDeletarTarefaExistente(){
        Tarefa tarefa = new Tarefa(1L, "Excluir", "Testar", false);
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(tarefa));

        boolean deletado = service.deletar(1L);

        assertTrue(deletado);
        verify(repository).deletar(1L);
    }

    @Test
    void naoDeveDeletarTarefaInexistente(){
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        boolean deletado = service.deletar(99L);

        assertFalse(deletado);
        verify(repository, never()).deletar(anyLong());

    }


    @Test
    void deveFiltrarTarefasPorStatus() {
        List<Tarefa> tarefas = List.of(
                new Tarefa(1L, "T1", "A", true),
                new Tarefa(2L, "T2", "B", false)
        );
        when(repository.listar()).thenReturn(tarefas);

        List<Tarefa> concluidas = service.listarPorStatus(true);

        assertEquals(1, concluidas.size());
        assertTrue(concluidas.get(0).isConcluida());
    }

    @Test
    void deveListarTarefasOrdenadasPorTitulo() {
        List<Tarefa> tarefas = List.of(
                new Tarefa(2L, "Zebra", "Ultima", false),
                new Tarefa(1L, "Abacate", "Primeira", true)
        );
        when(repository.listar()).thenReturn(tarefas);

        List<Tarefa> ordenadas = service.listarOrdenadasPorTitulo();

        assertEquals("Abacate", ordenadas.get(0).getTitulo());
        assertEquals("Zebra", ordenadas.get(1).getTitulo());
    }



}
