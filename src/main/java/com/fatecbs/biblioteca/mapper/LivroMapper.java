package com.fatecbs.biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.models.Livro;

@Component
public class LivroMapper {
	public Livro toEntity(LivroDto livroDTO) {
		Livro livro = new Livro();
		livro.setId(livroDTO.getId());
		livro.setTitulo(livroDTO.getTitulo());
		livro.setAutor(livroDTO.getAutor());
		livro.setIsbn(livroDTO.getIsbn());
        livro.setStatus(livroDTO.getStatus());
        livro.setDataDePublicacao(livroDTO.getDataDePublicacao());
		
		return livro;
	}
	public LivroDto toDTO(Livro livro) {
		LivroDto livroDto = new LivroDto();
		livroDto.setId(livro.getId());
		livroDto.setTitulo(livro.getTitulo());
		livroDto.setAutor(livro.getAutor());
		livroDto.setIsbn(livro.getIsbn());
        livroDto.setStatus(livro.getStatus());
        livroDto.setDataDePublicacao(livro.getDataDePublicacao());
		
		return livroDto;
	}	
	
public List<Livro> toEntity(List<LivroDto> livrosDTO) {
		return livrosDTO.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
	
	public List<LivroDto> toDTO(List<Livro> livros) {
		return livros.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}	

}
