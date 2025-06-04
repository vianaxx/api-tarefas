package com.tech.apitarefas.repository;

import com.tech.apitarefas.model.Tarefa;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TarefaRepository {
    private final Map<Long, Tarefa> tarefas = new HashMap<>();
    private final AtomicLong contador = new AtomicLong(1); //  AtomicLong contador gera um ID automático a cada nova tarefa

    public List<Tarefa> listar() {

        return new ArrayList<>(tarefas.values());
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return Optional.ofNullable(tarefas.get(id));
    }


    public Tarefa salvar(Tarefa tarefa) {
        if (tarefa.getId() == null) {
            tarefa.setId(contador.getAndIncrement());
        }
        tarefas.put(tarefa.getId(), tarefa);
        return tarefa;
    }

    public void deletar(Long id) {
        tarefas.remove(id);
    }
}
