package com.fatecbs.biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.models.Livro;

@Component
public class LivroMapper {
	public Livro toEntity(LivroDto livroDto) {
        Livro livro = new Livro();
        livro.setId(livroDto.getId());
        livro.setTitulo(livroDto.getTitulo());
        livro.setIsbn(livroDto.getIsbn());
        livro.setDataDePublicacao(livroDto.getDataDePublicacao());
        livro.setStatus(livroDto.getStatus());
        return livro;
    }

    public LivroDto toDTO(Livro livro) {
        LivroDto livroDto = new LivroDto();
        livroDto.setId(livro.getId());
        livroDto.setTitulo(livro.getTitulo());
        livroDto.setIsbn(livro.getIsbn());
        livroDto.setDataDePublicacao(livro.getDataDePublicacao());
        livroDto.setStatus(livro.getStatus());
        livroDto.setAutor(livro.getAutor().getId());
        return livroDto;
    }
	
	public List<Livro> toEntity(List<LivroDto> livrosDto) {
        return livrosDto.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<LivroDto> toDTO(List<Livro> livros) {
        return livros.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
