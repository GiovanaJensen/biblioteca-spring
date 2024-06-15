package com.fatecbs.biblioteca.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "cd_autor", nullable = false)
    private Autor autor;
    @Column(name = "cd_isbn", nullable = false)
    private String isbn;
    @Column(name = "dt_publicacao", nullable = false)
    private LocalDate dataDePublicacao;
    @Column(name = "nm_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public Livro(){
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
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
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
    public boolean isDatesValid() {
        if (dataDePublicacao != null && dataDePublicacao.isAfter(LocalDate.now())) {
          return false;
          }
        return true;
    }
}