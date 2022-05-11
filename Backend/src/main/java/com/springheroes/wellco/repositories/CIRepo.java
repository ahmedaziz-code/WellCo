package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.CI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CIRepo extends JpaRepository<CI, Integer> {
}
