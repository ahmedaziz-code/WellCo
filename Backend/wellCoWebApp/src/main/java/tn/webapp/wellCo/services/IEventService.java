package com.springheroes.wellco.services;

import java.util.List;
import com.springheroes.wellco.entities.Event;
import com.springheroes.wellco.enumeration.*;
import com.springheroes.wellco.entities.Office;
import com.springheroes.wellco.entities.User;

public interface IEventService {
	
	Event addEvent(Event e, EventType eventType, Integer idOffice);
	Office addOffice(Office off);
	List<Event> listOfEvent();
	List<User> getUsersByComment(Integer idEevent);
	List<User> getUsersByLikes(Integer IdEvent);
	void deletEvent(int idEvent);
	
	void commentEvent(Integer idEvent, Long idUser, String Comment);
	void uncommentEvent(Integer idComment);
	
	void noteEvent(Integer idEvent, EventNote eventNote, Long idUser);
	void removeNote(Integer idNote, Integer idEvent);
	
	void likeEvent(Integer idEvent, Long idUser);
	void removeLikeEvent(Integer idEvent, Long idUser);
	
	void joinEvent(Integer idEvent, Long idUser);
	void unjoinEvent(Integer idEvent, Long idUser);
	
	List<User> getUsersByCI(Long idUser);
	List<Event> getEventByOffice(Integer idOffice);
	
	void AddEventToFav(Integer idEvent, Long idUser);
	
	//void saveImage(MultipartFile imageFile) throws Exception;


}
