
package com.reparaciones.sistema.service;

import com.reparaciones.sistema.entity.Tecnico;
import com.reparaciones.sistema.repository.TecnicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    private final TecnicoRepository repository;

    public TecnicoService(TecnicoRepository repository) {
        this.repository = repository;
    }

    public List<Tecnico> listar() {
        return repository.findAll();
    }

    public Optional<Tecnico> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Tecnico guardar(Tecnico tecnico) {
        return repository.save(tecnico);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
