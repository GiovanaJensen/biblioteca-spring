package com.fatecbs.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatecbs.biblioteca.models.Autor;

@Repository
public interface AutorRepository extends
                      JpaRepository<Autor, Long>{}