package com.springheroes.wellco.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springheroes.wellco.entities.CommentEvent;
import com.springheroes.wellco.entities.Event;

public interface CommentEventRepository extends JpaRepository<CommentEvent, Integer> {

	//@Query("select c from CommentEvent c where c.idEvent=: idEvnt")
	List<CommentEvent> findByEvent(Event event);
	
	
	
}
