package com.fatecbs.biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.models.Livro;

@Component
public class LivroMapper {
    public Livro toEntity(LivroDto livroDto){
        Livro livro = new Livro();
        livro.setAutor(livroDto.getAutor());
        livro.setDataDePublicacao(livroDto.getDataDePublicacao());
        livro.setIsbn(livroDto.getIsbn());
        livro.setStatus(livroDto.getStatus());

        return livro;
    }

    public LivroDto toDto(Livro livro){
        LivroDto livroDto = new LivroDto();
        livroDto.setAutor(livro.getAutor());
        livroDto.setDataDePublicacao(livro.getDataDePublicacao());
        livroDto.setIsbn(livro.getIsbn());
        livroDto.setStatus(livro.getStatus());
        livroDto.setTitulo(livro.getTitulo());

        return livroDto;
    }

    public List<Livro> toEntity(List<LivroDto> criarLivroDto){
        return criarLivroDto.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<LivroDto> toDto(List<Livro> livros){
        return livros.stream().map(this::toDto).collect(Collectors.toList());
    }
}