package com.diegopigarri.bookstore.rosurce;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diegopigarri.bookstore.domain.Livro;
import com.diegopigarri.bookstore.dto.LivroDTO;
import com.diegopigarri.bookstore.service.LivroService;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

	@Autowired
	private LivroService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id) {
		Livro obj = service.findById(id);
		return ResponseEntity.ok().body(obj);

	}

	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll() {

		List<Livro> list = service.findAll();
		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);

	}

	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat,
			@RequestBody Livro obj) {
		Livro newObj = service.create(id_cat, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/livros/{id}").buildAndExpand(newObj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> update(@PathVariable Integer id, @RequestBody LivroDTO objDTO) {
		Livro newObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new LivroDTO(newObj));
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> updatePatch(@PathVariable Integer id, @RequestBody LivroDTO objDTO) {
		Livro newObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new LivroDTO(newObj));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
