package com.springheroes.wellco.services;

import java.util.List;

import com.springheroes.wellco.entities.*;
import com.springheroes.wellco.enumeration.*;
import org.springframework.web.multipart.MultipartFile;

public interface IEventService {
	Event addEvent(Event e, Integer idOffice);
	Event getEvent(Integer idEvent);
	Event findById(Integer idEvent);
	Office addOffice(Office off);
	List<Event> listOfEvent();
	List<User> getUsersByComment(Integer idEevent);
	List<User> getUsersByLikes(Integer IdEvent);
	List<Office> getAllOffices();
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
	List<CommentEvent> getCommentByEvent(Integer idEvent);
	
	//void saveImage(MultipartFile imageFile) throws Exception;


	Image saveImage(Image i);
	Image getFile(Integer fileId);

}
