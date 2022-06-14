package com.diegopigarri.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.diegopigarri.bookstore.domain.Categoria;
import com.diegopigarri.bookstore.dto.CategoriaDTO;
import com.diegopigarri.bookstore.ropository.CategoriaRepository;
import com.diegopigarri.bookstore.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));

	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Categoria create(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDTO) {
		Categoria obj = findById(id);
		obj.setNome(objDTO.getNome());
		obj.setDescricao(objDTO.getDescricao());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.diegopigarri.bookstore.service.exception.DataIntegrityViolationException("Objeto não pode ser deletado! Possui livros associados");
		}
		
	}

}
