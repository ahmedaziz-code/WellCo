package com.springheroes.wellco.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springheroes.wellco.entities.Event;
import com.springheroes.wellco.entities.Office;
import com.springheroes.wellco.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    /*@Query("select e from Event e inner join e.users u where e.idEvent=:idEvent and u.idUser=:idUser")
    Event retrieveEventByUserIdAndEventId(@Param("idEvent") Integer idEvent, @Param("idUser") Long IdUser);*/
    
    
    List<Event> findByUsers(User user);
    
    List<Event> findByOffices(Office office);
    
    

    

}
