package com.fatecbs.biblioteca.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.models.Status;
import com.fatecbs.biblioteca.repository.BibliotecaRepository;

@Service
public class BibliotecaService implements IService<Livro>{
    @Autowired
	private BibliotecaRepository repository;

    private List<Livro> biblioteca = new ArrayList<>();
    
    // public BibliotecaService(){
    //     Livro livro1 = new Livro(1L);
    //     livro1.setAutor("Shakespeare");
    //     livro1.setDataDePublicacao(LocalDate.of(2024, 4, 24));
    //     livro1.setIsbn("12333");
    //     livro1.setStatus(Status.DISPONIVEL);
    //     livro1.setTitulo("Hamlet");
    //     biblioteca.add(livro1);
    // }

    @Override
    public List<Livro> findAll(){
        return repository.findAll();
    }

    @Override
    public Livro findById(Long id){
        Optional<Livro> obj = repository.findById(id);
		return obj.orElse(null);
    }

    @Override
    public Livro create(Livro livro){
        repository.save(livro);
		return livro;
    }

    @Override
    public boolean update(Livro livro){
        if (repository.existsById(livro.getId())) {
			repository.save(livro);
			return true;
		}
		return false;
    }

    @Override
    public boolean delete(Long id){
        if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
    }
}