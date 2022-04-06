package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  OfficeRepo extends JpaRepository<Office,Integer> {

}
