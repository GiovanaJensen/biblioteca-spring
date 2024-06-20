package com.fatecbs.biblioteca.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fatecbs.biblioteca.dto.AutorDto;
import com.fatecbs.biblioteca.models.Autor;

@Component
public class AutorMapper {

    @Autowired
    private LivroMapper livroMapper;

    public Autor toEntity(AutorDto autorDto){
        Autor autor = new Autor();
        autor.setId(autorDto.getId());
        autor.setNome(autorDto.getNome());

        return autor;
    }

    public AutorDto toDTO(Autor autor) {
        AutorDto autorDto = new AutorDto();
        autorDto.setId(autor.getId());
        autorDto.setNome(autor.getNome());

        return autorDto;
    }  
	
    public List<Autor> toEntity(List<AutorDto> autoresDto) {
		return autoresDto.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
	
	public List<AutorDto> toDTO(List<Autor> autores) {
		return autores.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}	
}
