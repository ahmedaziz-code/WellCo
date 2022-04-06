package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
