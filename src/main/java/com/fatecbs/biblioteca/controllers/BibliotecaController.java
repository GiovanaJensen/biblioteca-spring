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

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.LivroMapper;
import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.services.BibliotecaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class BibliotecaController{

    @Autowired
    private BibliotecaService service;

    @Autowired
    private LivroMapper mapper;

    @GetMapping("")
    public ResponseEntity<List<LivroDto>> getAll(){
        List<Livro> livros = service.findAll();
        if (!livros.isEmpty()) {
            return ResponseEntity.ok(mapper.toDTO(livros));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<LivroDto> get(@PathVariable("id") Long id){
        Livro livro = service.findById(id);
		if (livro != null) {
			return ResponseEntity.ok(mapper.toDTO(livro));
		}
		return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LivroDto> post(@RequestBody LivroDto livro) {
            Livro createdLivro = service.create(mapper.toEntity(livro));
            URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdLivro.getId())
                            .toUri();
            return ResponseEntity.created(location).body(mapper.toDTO(createdLivro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDto> put(@PathVariable("id") Long id, @RequestBody LivroDto livro){
            livro.setId(id);
            service.update(mapper.toEntity(livro));
            return ResponseEntity.ok(livro);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LivroDto> patch(@PathVariable("id") Long id, @RequestBody LivroDto livro) {
            Livro existingLivro = service.findById(id);
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

            return ResponseEntity.ok(mapper.toDTO(existingLivro));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<LivroDto> delete(@PathVariable("id") Long id){
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}