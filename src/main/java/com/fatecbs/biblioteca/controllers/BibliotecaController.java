package com.fatecbs.biblioteca.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class BibliotecaController{

    @GetMapping("")
    public String getLivros(){
        return "Tudo funcionando";
    }
}