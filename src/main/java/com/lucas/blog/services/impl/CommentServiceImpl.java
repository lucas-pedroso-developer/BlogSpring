package com.lucas.blog.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.blog.entities.Comment;
import com.lucas.blog.exception.BusinessRuleException;
import com.lucas.blog.repositories.CommentRepository;
import com.lucas.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	@Transactional
	public Comment saveComment(Comment obj) {				
		return commentRepository.save(obj);
	}
	
	@Override
	@Transactional
	public Comment updateComment(Long id, Comment obj) {
		try {
			Comment entity = commentRepository.getOne(id);			
			entity.setAuthor(obj.getAuthor());
			entity.setText(obj.getText());
			entity.setDate(obj.getDate());						
			return commentRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new BusinessRuleException(e.getMessage());
		}
		
	}

	@Override
	@Transactional
	public void deleteComment(Comment obj) {
		commentRepository.delete(obj);
		
	}

	@Override
	public Optional<Comment> getCommentById(long id) {
		return commentRepository.findById(id);
	}
	
	@Override
	public List<Comment> getAllComments() {		
		return commentRepository.findAll();
	}
	

}
