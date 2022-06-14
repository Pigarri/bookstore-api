package com.diegopigarri.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diegopigarri.bookstore.domain.Categoria;
import com.diegopigarri.bookstore.domain.Livro;
import com.diegopigarri.bookstore.dto.LivroDTO;
import com.diegopigarri.bookstore.ropository.LivroRepository;
import com.diegopigarri.bookstore.service.exception.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Integer id) {
		Optional<Livro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Livro.class.getName()));

	}

	public List<Livro> findAll() {
		return repository.findAll();
	}

	public Livro create(Integer id_cat, Livro obj) {
		obj.setId(null);
		Categoria cat = categoriaService.findById(id_cat);
		obj.setCategoria(cat);
		return repository.save(obj);
	}

	public Livro update(Integer id, LivroDTO objDTO) {
		Livro obj = findById(id);
		obj.setNome_autor(objDTO.getNome_autor());
		obj.setTexto(objDTO.getTexto());
		obj.setTitulo(objDTO.getTitulo());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

}
