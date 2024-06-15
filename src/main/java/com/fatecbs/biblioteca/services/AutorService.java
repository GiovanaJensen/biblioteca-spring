package com.fatecbs.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.repository.AutorRepository;

@Service
public class AutorService implements IService<Autor>{

    @Autowired
    private AutorRepository repository;

    public Autor findByNome(String nome){
        Optional<Autor> autor = repository.findByNome(nome);
        return autor.orElse(null);
    }

    @Override
    public Autor create(Autor autor) {
        return repository.save(autor);
    }

    @Override
    public Autor findById(Long id) {
        Optional<Autor> obj = repository.findById(id);
        return obj.orElse(null);
    }

    @Override
    public List<Autor> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean update(Long id, Autor autor) {
        if(repository.existsById(id)){
            autor.setId(id);
            repository.save(autor);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}