package com.fatecbs.biblioteca.dto;

import java.time.LocalDate;

import com.fatecbs.biblioteca.models.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LivroDto {

    private Long id;

    @NotBlank(message = "Titulo não pode ser nulo")
    private String titulo;

    @NotNull(message = "Autor não pode ser nulo")
    private Long cdAutor;

    @NotBlank(message = "Isbn não pode ser nulo")
    private String isbn;

    @NotNull(message = "Data de publicação não pode ser nulo")
    private LocalDate dataDePublicacao;

    @NotNull(message = "Status não pode ser nulo")
    private Status status;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getCdAutor() {
        return cdAutor;
    }

    public void setCdAutor(Long cdAutor) {
        this.cdAutor = cdAutor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataDePublicacao() {
        return dataDePublicacao;
    }

    public void setDataDePublicacao(LocalDate dataDePublicacao) {
        this.dataDePublicacao = dataDePublicacao;
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