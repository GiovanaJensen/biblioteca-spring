package com.fatecbs.biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.services.BibliotecaService;

@RestController
@RequestMapping("/livros")
public class BibliotecaController{

    private BibliotecaService service;

    @GetMapping("")
    public ResponseEntity<List<Livro>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Livro> get(@PathVariable("id") Long id){
        Livro livro = service.findById(id);
        if (livro != null){
            return ResponseEntity.ok(livro);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Livro> post(@RequestBody Livro livro){
        service.create(livro);
        URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(livro.getId())
						.toUri();
		return ResponseEntity.created(location).body(livro);
    }

    @PutMapping
    public ResponseEntity<Livro> put(@RequestBody Livro livro){
        if(service.update(livro)){
            return ResponseEntity.ok(livro);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<Livro> patch(@RequestBody Livro livro){
        if(service.update(livro)){
            return ResponseEntity.ok(livro);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Livro> delete(@PathVariable("id") Long id){
        if(service.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}