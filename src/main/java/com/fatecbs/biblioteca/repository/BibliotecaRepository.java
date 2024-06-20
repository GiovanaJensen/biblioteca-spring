package com.fatecbs.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.models.Status;

@Repository
public interface BibliotecaRepository extends
                      JpaRepository<Livro, Long>{
    List<Livro> findByStatus(Status status);
    @Query("SELECT l from Livro l WHERE l.autor.id = :autorId")
    List<Livro> findByAutorId(Long autorId);
}