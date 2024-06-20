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

import com.fatecbs.biblioteca.dto.AutorDto;
import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.AutorMapper;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.services.AutorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping
public class AutorController {
    @Autowired
    private AutorService service;

    @Autowired
    private AutorMapper mapper;

    @Operation(summary = "Listar todos os autores", description = "Lista todos os autores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores encontrada", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AutorDto.class))) }),
            @ApiResponse(responseCode = "404", description = "Nenhum autor encontrado")
    })
    @GetMapping("")
    public ResponseEntity<List<AutorDto>> getAll(){
        List<Autor> autores = service.findAll();
        if (!autores.isEmpty()) {
            return ResponseEntity.ok(mapper.toDTO(autores));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar autor por ID", description = "Buscar um autor pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(value="/{id}")
    public ResponseEntity<AutorDto> get(@PathVariable("id") Long id){
        try{
            Autor autor = service.findById(id);
            if (autor != null) {
                return ResponseEntity.ok(mapper.toDTO(autor));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @Operation(summary = "Criar um novo autor", description = "Cria um novo autor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "422", description = "Campos inválidos ou inconsistências nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<AutorDto> post(@RequestBody AutorDto autor) {
        try{
            Autor createdAutor = service.create(mapper.toEntity(autor));
            URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdAutor.getId())
                            .toUri();
            return ResponseEntity.created(location).body(mapper.toDTO(createdAutor));
        }catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro na integridade dos dados.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }
    }

    @Operation(summary = "Atualizar um autor existente", description = "Atualiza um autor pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDto.class)) }),
            @ApiResponse(responseCode = "400", description = "JSON inválido"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorDto> put(@PathVariable("id") Long id, @RequestBody AutorDto autor){
        try{
            autor.setId(id);
            boolean updated = service.update(mapper.toEntity(autor));

            if (updated) {
                Autor updatedAutor = service.findById(id);
                return ResponseEntity.ok(mapper.toDTO(updatedAutor));
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado.", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", ex);
        }   
    }
   
    @Operation(summary = "Excluir um autor", description = "Exclui um autor pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
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