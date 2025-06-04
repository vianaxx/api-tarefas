package com.tech.apitarefas.service;

import com.tech.apitarefas.model.Tarefa;
import com.tech.apitarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public List<Tarefa> listarTodas() {
        return repository.listar();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public Tarefa criarOuAtualizar(Tarefa tarefa) {
        return repository.salvar(tarefa);
    }

    public boolean deletar(Long id) {
        Optional<Tarefa> tarefa = repository.buscarPorId(id);
        if (tarefa.isPresent()) {
            repository.deletar(id);
            return true;
        }
        return false;
    }


    public List<Tarefa> listarPorStatus(boolean concluida) {
        return listarTodas().stream()
                .filter(tarefa -> tarefa.isConcluida() == concluida)
                .toList(); // ou collect(Collectors.toList()) se JAVA < 16
    }

    public List<Tarefa> listarOrdenadasPorTitulo() {
        return listarTodas().stream()
                .sorted((t1, t2) -> t1.getTitulo().compareToIgnoreCase(t2.getTitulo()))
                .toList();
    }


}
