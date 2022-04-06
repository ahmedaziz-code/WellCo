package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Event;
import com.springheroes.wellco.entities.LikeEvent;
import com.springheroes.wellco.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  LikeEventRepo extends JpaRepository<LikeEvent,Integer> {
	List<LikeEvent> findByEvent(Event event);
	List<LikeEvent> findByUser(User user);
}
