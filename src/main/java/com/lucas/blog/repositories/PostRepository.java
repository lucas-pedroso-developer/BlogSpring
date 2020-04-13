package com.lucas.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.blog.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
