package com.fatecbs.biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private BibliotecaService service;

    @GetMapping("")
    public ResponseEntity<List<Livro>> getAll(){
        List<Livro> livros = service.findAll();
        if (!livros.isEmpty()) {
            return ResponseEntity.ok(livros);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<?> post(@RequestBody Livro livro) {
        try {
            if (livro == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O livro não pode ser nulo");
            }
            
            if (!livro.isDatesValid()) { 
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Datas inválidas: a data de início não pode ser posterior à data final.");
            }

            Livro createdLivro = service.create(livro);
            URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdLivro.getId())
                            .toUri();
            return ResponseEntity.created(location).body(createdLivro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Livro livro){
        try {
            if (livro == null || livro.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O livro ou o ID do livro não podem ser nulos");
            }
            Livro existingLivro = service.findById(id);
            if (existingLivro == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro com ID " + id + " não encontrado");
            }
            livro.setId(id);
            service.update(livro);
            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("JSON inválido ou campos não parseáveis.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") Long id, @RequestBody Livro livro) {
        try {
            if (livro == null || livro.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O livro ou o ID do livro não podem ser nulos");
            }
            Livro existingLivro = service.findById(id);
            if (existingLivro == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro com ID " + id + " não encontrado");
            }
            if (livro.getTitulo() != null) {
                existingLivro.setTitulo(livro.getTitulo());
            }
            if (livro.getAutor() != null) {
                existingLivro.setAutor(livro.getAutor());
            }
            if (livro.getIsbn() != null) {
                existingLivro.setIsbn(livro.getIsbn());
            }
            if (livro.getDataDePublicacao() != null) {
                existingLivro.setDataDePublicacao(livro.getDataDePublicacao());
            }
            if (livro.getStatus() != null) {
                existingLivro.setStatus(livro.getStatus());
            }

            service.update(existingLivro);

            return ResponseEntity.ok(existingLivro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("JSON inválido ou campos não parseáveis.");
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Livro> delete(@PathVariable("id") Long id){
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}