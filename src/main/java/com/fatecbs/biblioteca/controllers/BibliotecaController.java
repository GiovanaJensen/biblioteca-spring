package com.fatecbs.biblioteca.controllers;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.LivroMapper;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.services.AutorService;
import com.fatecbs.biblioteca.services.LivroService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

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
        if (!livros.isEmpty()) {
            return ResponseEntity.ok(livros);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        try {
            LivroDto livroDto = service.findById(id);
            if (livroDto != null) {
                return ResponseEntity.ok(livroDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @PostMapping("")
    public ResponseEntity<LivroDto> post(@Valid @RequestBody LivroDto livroDto) {
        try {
            LivroDto createdLivroDto = service.create(livroDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdLivroDto.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdLivroDto);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro na integridade dos dados.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody LivroDto livroDto) {
        try {
            livroDto.setId(id);

            boolean updated = service.update(id, livroDto);
            if (updated) {
                LivroDto updatedLivroDto = service.findById(id);
                return ResponseEntity.ok(updatedLivroDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
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
            if (updated) {
                return ResponseEntity.ok(existingLivro);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            if (service.delete(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build(); 
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
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