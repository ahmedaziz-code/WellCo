package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  FavouriteRepo extends JpaRepository<Favourite,Integer> {

}
