package com.fatecbs.biblioteca.dto;

import java.time.LocalDate;

import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Status;

import jakarta.validation.constraints.NotBlank;

public class LivroDto {
    @NotBlank(message ="Id não pode ser nulo")
    private Long id;

    @NotBlank(message = "Titulo não pode ser nulo")
    private String titulo;

    @NotBlank(message = "Autor não pode ser nulo")
    private Long cdAutor;

    @NotBlank(message = "Isbn não pode ser nulo")
    private String isbn;

    @NotBlank(message = "Data de publicação não pode ser nulo")
    private LocalDate dataDePublicacao;

    @NotBlank(message = "Status não pode ser nulo")
    private Status status;

   public Long getCdAutor() {
       return cdAutor;
   }

   public void setCdAutor(Long cdAutor) {
       this.cdAutor = cdAutor;
   }

    public LocalDate getDataDePublicacao() {
        return dataDePublicacao;
    }

    public void setDataDePublicacao(LocalDate dataDePublicacao) {
        this.dataDePublicacao = dataDePublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}