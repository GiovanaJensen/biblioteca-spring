package com.fatecbs.biblioteca.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.LivroMapper;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.services.AutorService;
import com.fatecbs.biblioteca.services.LivroService;

@RestController
@RequestMapping("/livros")
public class BibliotecaController implements IController<LivroDto> {

    @Autowired
    private LivroService service;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LivroMapper mapper;

    @GetMapping("")
    public ResponseEntity<List<LivroDto>> getAll() {
        List<LivroDto> livros = service.findAll();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        LivroDto livroDto = service.findById(id);
        if (livroDto != null) {
            return ResponseEntity.ok(livroDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<LivroDto> post(@RequestBody LivroDto livroDto) {
        LivroDto createdLivroDto = service.create(livroDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLivroDto.getCdAutor())
                .toUri();
        return ResponseEntity.created(location).body(createdLivroDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody LivroDto livroDto) {
        livroDto.setCdAutor(id);
        boolean updated = service.update(id, livroDto);
        if(updated){
            LivroDto updatedLivroDto = service.findById(id);
            return ResponseEntity.ok(updatedLivroDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") Long id, @RequestBody LivroDto livroDto) {
        LivroDto existingLivro = service.findById(id);
        if (existingLivro != null) {
            if (livroDto.getTitulo() != null) {
                existingLivro.setTitulo(livroDto.getTitulo());
            }
            if (livroDto.getCdAutor() != null) {
                existingLivro.setCdAutor(livroDto.getCdAutor());
            }
            if (livroDto.getIsbn() != null) {
                existingLivro.setIsbn(livroDto.getIsbn());
            }
            if (livroDto.getDataDePublicacao() != null) {
                existingLivro.setDataDePublicacao(livroDto.getDataDePublicacao());
            }
            if (livroDto.getStatus() != null) {
                existingLivro.setStatus(livroDto.getStatus());
            }
            boolean updated = service.update(id, existingLivro);
            if(updated){
                return ResponseEntity.ok(existingLivro);
            }
        } 
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/autor/nome/{nome}")
    public ResponseEntity<List<String>> getTitulosByAutorNome(@PathVariable String nome) {
        Autor autor = autorService.findByNome(nome);
        if (autor == null) {
            return ResponseEntity.notFound().build();
        }
        List<LivroDto> livros = service.findByAutor(autor).stream()
                                             .map(mapper::toDto)
                                             .collect(Collectors.toList());
        List<String> titulos = livros.stream()
                                     .map(LivroDto::getTitulo)
                                     .collect(Collectors.toList());
        return ResponseEntity.ok(titulos);
    }
}