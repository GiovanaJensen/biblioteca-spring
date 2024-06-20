package com.fatecbs.biblioteca.controllers;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.LivroMapper;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.services.AutorService;
import com.fatecbs.biblioteca.services.BibliotecaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/livros")
public class BibliotecaController{

    @Autowired
    private BibliotecaService service;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LivroMapper mapper;

    @Operation(summary = "Listar todos os livros", description = "Lista todos os livros cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros encontrada", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LivroDto.class))) }),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado")
    })
    @GetMapping("")
    public ResponseEntity<List<LivroDto>> getAll(){
        List<Livro> livros = service.findAll();
        if (!livros.isEmpty()) {
            return ResponseEntity.ok(mapper.toDTO(livros));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar livro por ID", description = "Buscar um livro pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(value="/{id}")
    public ResponseEntity<LivroDto> get(@PathVariable("id") Long id){
        try{
            Livro livro = service.findById(id);
            if (livro != null) {
                return ResponseEntity.ok(mapper.toDTO(livro));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @Operation(summary = "Criar um novo livro", description = "Cria um novo livro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "422", description = "Campos inválidos ou inconsistências nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<LivroDto> post(@RequestBody LivroDto livroDto) {
        try{
            Autor autor = autorService.findById(livroDto.getAutor());
            if(autor == null){
                return ResponseEntity.unprocessableEntity().build();
            }

            Livro livro = mapper.toEntity(livroDto);
            livro.setAutor(autor);

            Livro createdLivro = service.create(livro);
            URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdLivro.getId())
                            .toUri();
            return ResponseEntity.created(location).body(mapper.toDTO(createdLivro));
        }catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro na integridade dos dados.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @Operation(summary = "Atualizar um livro existente", description = "Atualiza um livro pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDto.class)) }),
            @ApiResponse(responseCode = "400", description = "JSON inválido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivroDto> put(@PathVariable("id") Long id, @RequestBody LivroDto livroDto){
        try{
            Livro existingLivro = service.findById(id);

            if(existingLivro == null){
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorService.findById(livroDto.getAutor());
            if(autor == null){
                return ResponseEntity.unprocessableEntity().build();
            }

            existingLivro.setTitulo(livroDto.getTitulo());
            existingLivro.setAutor(autor);
            existingLivro.setIsbn(livroDto.getIsbn());
            existingLivro.setStatus(livroDto.getStatus());
            existingLivro.setDataDePublicacao(livroDto.getDataDePublicacao());

            service.update(existingLivro);
            return ResponseEntity.ok(mapper.toDTO(existingLivro));

        }catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }   
    }

    @Operation(summary = "Atualizar parcialmente um livro existente", description = "Atualiza parcialmente um livro pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDto.class)) }),
            @ApiResponse(responseCode = "400", description = "JSON inválido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LivroDto> patch(@PathVariable("id") Long id, @RequestBody LivroDto livro) {
            Livro existingLivro = service.findById(id);
            if(existingLivro != null){
                if (livro.getTitulo() != null) {
                    existingLivro.setTitulo(livro.getTitulo());
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

                boolean updated = service.update(existingLivro);
                if (updated) {
                    return ResponseEntity.ok(mapper.toDTO(existingLivro));
                }
            }
            return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Excluir um livro", description = "Exclui um livro pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(value="/{id}")
    public ResponseEntity<LivroDto> delete(@PathVariable("id") Long id){
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
}