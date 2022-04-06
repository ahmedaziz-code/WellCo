package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Eval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  EvalRepo extends JpaRepository<Eval,Integer> {

}
