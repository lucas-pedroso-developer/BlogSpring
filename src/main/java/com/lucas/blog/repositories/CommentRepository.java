package com.lucas.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
