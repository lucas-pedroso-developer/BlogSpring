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
import com.lucas.blog.dto.CommentDto;
import com.lucas.blog.entities.Comment;
import com.lucas.blog.entities.Post;
import com.lucas.blog.exception.BusinessRuleException;
import com.lucas.blog.services.CommentService;
import com.lucas.blog.services.PostService;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

	@Autowired
	private CommentService service;
	
	@Autowired
	private PostService postService;

	@GetMapping
	public ResponseEntity<List<Comment>> findAll() {
		List<Comment> list = service.getAllComments(); 
		System.out.print(list);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Comment>> getCommentById(@PathVariable long id) {
		Optional<Comment> obj = service.getCommentById(id);
		return ResponseEntity.ok().body(obj);
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("")
	public ResponseEntity saveComment(@RequestBody CommentDto dto) {
		try {
 			Comment comment = convert(dto);
			comment = service.saveComment(comment);
			return new ResponseEntity(comment, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
			
	private Comment convert(CommentDto dto) {
		
		Comment comment = new Comment();
		comment.setId(dto.getId());
		comment.setAuthor(dto.getAuthor());
		comment.setText(dto.getText());
		comment.setDate(dto.getDate());
						
		Post post = postService
				.getPostById(dto.getPost())
				.orElseThrow(() -> new BusinessRuleException("Post não encontrado!"));				
		comment.setPost(post);
		
		return comment;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(value = "{id}")
	public ResponseEntity updateComment(@PathVariable Long id, @RequestBody Comment dto) {
		System.out.print(id);
		System.out.print(dto);
		return service.getCommentById(id).map(entity -> {			
			try {
				entity.setText(dto.getText());
				service.updateComment(entity.getId(), entity);
				return ResponseEntity.ok(entity);
			} catch (BusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> 
			new ResponseEntity("Comentário não encontrado na base de dados", HttpStatus.BAD_REQUEST)  );
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable long id) {
		return service.getCommentById(id).map(entity -> {
			service.deleteComment(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de Dados", HttpStatus.BAD_REQUEST) );
	}
	
}
