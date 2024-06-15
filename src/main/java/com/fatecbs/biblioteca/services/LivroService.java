package com.fatecbs.biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatecbs.biblioteca.dto.LivroDto;
import com.fatecbs.biblioteca.mapper.LivroMapper;
import com.fatecbs.biblioteca.models.Autor;
import com.fatecbs.biblioteca.models.Livro;
import com.fatecbs.biblioteca.repository.BibliotecaRepository;

@Service
public class LivroService implements IService<LivroDto>{
    @Autowired
	private BibliotecaRepository repository;

    @Autowired
    private LivroMapper livroMapper;

    private List<Livro> biblioteca = new ArrayList<>();

    public List<Livro> findByAutor(Autor autor){
        return repository.findByAutor(autor);
    }

    @Override
    public List<LivroDto> findAll(){
        List<Livro> livros = repository.findAll();
        return livros.stream().map(livroMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public LivroDto findById(Long id){
        Optional<Livro> obj = repository.findById(id);
		return obj.map(livroMapper::toDto).orElse(null);
    }

    @Override
    public LivroDto create(LivroDto livroDto){
        Livro livro = livroMapper.toEntity(livroDto);
        Livro livroSalvo = repository.save(livro);
        return livroMapper.toDto(livroSalvo);
    }

    @Override
    public boolean update(Long id, LivroDto livroDto){
        if (repository.existsById(id)) {
            Livro livro = livroMapper.toEntity(livroDto);
            livro.setId(id);
			repository.save(livro);
			return true;
		}
		return false;
    }

    @Override
    public boolean delete(Long id){
        if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
    }
}