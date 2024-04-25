package com.fatecbs.biblioteca.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.models.Status;

public class BibliotecaService{
    private List<Livro> biblioteca = new ArrayList<>();
    
    public BibliotecaService(){
        Livro livro1 = new Livro(1L);
        livro1.setAutor("Shakespeare");
        livro1.setDataDePublicacao(LocalDate.of(2024, 4, 24));
        livro1.setIsbn("12333");
        livro1.setStatus(Status.DISPONIVEL);
        livro1.setTitulo("Hamlet");
        biblioteca.add(livro1);
    }

    public Livro find(Livro livro){
        for(Livro l:biblioteca){
            if(l.equals(livro)){
                return livro;
            }
        }
        return null;
    }

    public Livro find(Long id){
        Livro l = new Livro(id);
        return find(l);
    }

    public List<Livro> findAll(){
        return biblioteca;
    }

    public void create(Livro livro){
        livro.setId(livro.generateId());
        biblioteca.add(livro);
    }

    public Boolean update(Livro livro){
        Livro _livro = this.find(livro);
        if(_livro != null){
            _livro.setAutor(livro.getAutor());
            _livro.setDataDePublicacao(livro.getDataDePublicacao());
            _livro.setIsbn(livro.getIsbn());
            _livro.setStatus(livro.getStatus());
            _livro.setTitulo(livro.getTitulo());
            return true;
        }
        return false;
    }

    public Boolean delete(Long id){
        Livro _livro = this.find(id);
        if(_livro != null){
            biblioteca.remove(_livro);
            return true;
        }
        return false;
    }
}