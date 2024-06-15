package com.fatecbs.biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.services.AutorService;

@Component
public class LivroMapper {

    @Autowired
    private AutorService autorService;

    public Livro toEntity(LivroDto livroDto) {
        Livro livro = new Livro();
        livro.setTitulo(livroDto.getTitulo());
        livro.setDataDePublicacao(livroDto.getDataDePublicacao());
        livro.setIsbn(livroDto.getIsbn());
        livro.setStatus(livroDto.getStatus());

        Autor autor = autorService.findById(livroDto.getCdAutor());
        livro.setAutor(autor);

        return livro;
    }

    public LivroDto toDto(Livro livro) {
        LivroDto livroDto = new LivroDto();
        livroDto.setTitulo(livro.getTitulo());
        livroDto.setDataDePublicacao(livro.getDataDePublicacao());
        livroDto.setIsbn(livro.getIsbn());
        livroDto.setStatus(livro.getStatus());
        livroDto.setCdAutor(livro.getAutor().getId());

        return livroDto;
    }

    public List<Livro> toEntity(List<LivroDto> livroDtos) {
        return livroDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<LivroDto> toDto(List<Livro> livros) {
        return livros.stream().map(this::toDto).collect(Collectors.toList());
    }
}