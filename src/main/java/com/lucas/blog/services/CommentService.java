package com.lucas.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lucas.blog.entities.Comment;
import com.lucas.blog.entities.Post;

@Service
public interface CommentService {

	Comment saveComment(Comment obj);	
	Comment updateComment(Long id, Comment obj);
	void deleteComment(Comment obj);
	Optional<Comment> getCommentById(long id);
	List<Comment> getAllComments();
	
}
