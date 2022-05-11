package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  BadgeRepo extends JpaRepository<Badge,Integer> {

}
