package com.lucas.blog.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucas.blog.entities.Post;
import com.lucas.blog.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;

	@GetMapping
	public ResponseEntity<List<Post>> findAll() {
		List<Post> list = service.getAllPosts(); 
		System.out.print(list);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Post>> getPostById(@PathVariable long id) {
		Optional<Post> obj = service.getPostById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("")
	public ResponseEntity<Post> savePost(@RequestBody Post obj) {
		obj = service.savePost(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
		

	@PutMapping(value = "/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post obj) {
		obj = service.updatePost(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletePost(@PathVariable long id) {
		return service.getPostById(id).map(entity -> {
			service.deletePost(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de Dados", HttpStatus.BAD_REQUEST) );
	}	
		
}
