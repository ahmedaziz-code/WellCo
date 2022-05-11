package com.springheroes.wellco.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springheroes.wellco.entities.Event;
import com.springheroes.wellco.entities.LikeEvent;
import com.springheroes.wellco.entities.NoteE;
import com.springheroes.wellco.entities.User;

public interface NoteERepository extends JpaRepository<NoteE, Integer> {
	List<NoteE> findByEvent(Event event);
	List<NoteE> findByUser(User user);
	List<NoteE> findByUserAndEvent(User user, Event event);

}
