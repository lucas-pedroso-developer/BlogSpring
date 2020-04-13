package com.lucas.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucas.blog.entities.Post;

@Service
public interface PostService {

	Post savePost(Post obj);	
	Post updatePost(Long id, Post obj);
	void deletePost(Post obj);
	Optional<Post> getPostById(long id);	
	List<Post> getAllPosts();
		
}
