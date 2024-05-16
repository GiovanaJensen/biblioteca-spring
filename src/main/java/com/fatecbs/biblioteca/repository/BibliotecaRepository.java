package com.fatecbs.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatecbs.biblioteca.models.Livro;

@Repository
public interface BibliotecaRepository extends
                      JpaRepository<Livro, Long>{}