package com.lucas.blog.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.blog.entities.Post;
import com.lucas.blog.repositories.PostRepository;
import com.lucas.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@Override		
	@Transactional
	public Post savePost(Post user) {		
		return postRepository.save(user);
	}

	@Override
	@Transactional
	public Post updatePost(Long id, Post obj) { 
		//return postRepository.save(obj);
		try {
			Post entity = postRepository.getOne(id);
			
			entity.setAuthor(obj.getAuthor());
			entity.setTitle(obj.getTitle());
			entity.setText(obj.getText());
			entity.setDate(obj.getDate());
						
			return postRepository.save(entity);
		} catch (EntityNotFoundException e) {
			//throw new ResourceNotFoundException(e.getMessage());
		}	
		return null;
	}

	@Override
	@Transactional
	public void deletePost(Post user) {
		postRepository.delete(user);		
	}
	
	@Override
	public Optional<Post> getPostById(long id) {		
		return postRepository.findById(id);
	}

	@Override
	public List<Post> getAllPosts() {		
		return postRepository.findAll();
	}
	
}
