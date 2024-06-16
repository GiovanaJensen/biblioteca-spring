package com.fatecbs.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Livro;
import java.util.List;


@Repository
public interface BibliotecaRepository extends
                      JpaRepository<Livro, Long>{
    List<String> buscarTitulosPorStatusDisponivel();
    List<Livro> findByAutor(Autor autor);
}