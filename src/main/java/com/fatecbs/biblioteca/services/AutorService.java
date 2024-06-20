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

    @Override
    public List<Autor> findAll(){
        return repository.findAll();
    }

    @Override
    public Autor findById(Long id){
        Optional<Autor> autor = repository.findById(id);
        return autor.orElse(null);
    }

    @Override
    public Autor create(Autor autor){
        repository.save(autor);
        return autor;
    }

    @Override
    public boolean update(Autor autor){
        if(repository.existsById(autor.getId())){
            repository.save(autor);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
