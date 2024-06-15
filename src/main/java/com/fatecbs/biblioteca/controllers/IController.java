package com.fatecbs.biblioteca.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IController<T> {
   ResponseEntity<List<T>> getAll();
   ResponseEntity<?> get(Long id);
   ResponseEntity<T> post(T obj);
   ResponseEntity<?> put(Long id ,T obj);
   ResponseEntity<?> patch(Long id, T obj);
   ResponseEntity<?> delete(Long id);
}