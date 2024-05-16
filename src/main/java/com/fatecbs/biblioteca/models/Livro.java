package com.fatecbs.biblioteca.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tb_livro")
public class Livro implements Serializable{
    private static final long serialVersionUID = -4205156507257923921L;
    public static Long nextId = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nm_titulo", nullable = false)
    private String titulo;
    @Column(name = "nm_autor", nullable = false)
    private String autor;
    @Column(name = "cd_isbn", nullable = false)
    private String isbn;
    @Column(name = "dt_publicacao", nullable = false)
    private LocalDate dataDePublicacao;
    @Column(name = "nm_status", nullable = false)
    private Status status;

    public Livro(Long id){
        this.id = id;
    }

    public Long generateId(){
        return nextId++;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
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
}