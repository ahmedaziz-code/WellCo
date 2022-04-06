package com.springheroes.wellco.controllers;

import java.util.List;
import com.springheroes.wellco.enumeration.*;
import com.springheroes.wellco.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springheroes.wellco.services.IEventService;

@RestController
@RequestMapping("/Event")
public class EventController {
	
	@Autowired
	IEventService eventService;

	@PostMapping("/addEvent/{eventType}/{idOffice}")
	@ResponseBody
	public Event addEvent(@RequestBody Event e, @PathVariable("eventType") EventType eventType, @PathVariable("idOffice") Integer idOffice) {
		return eventService.addEvent(e, eventType, idOffice);
	}
	
	@PostMapping("/addOffice")
	@ResponseBody
	public Office addOffice(@RequestBody Office off) {
		return eventService.addOffice(off);
	}
	
	@DeleteMapping("/deleteEvent/{idEvent}")
	@ResponseBody
	public void deleteEvent(@PathVariable("idEvent") Integer idEvent) {
		eventService.deletEvent(idEvent);
	}
	
	@DeleteMapping("/deleteNote/{idNote}/{idEvent}")
	 @ResponseBody
	 public void removeNote(@PathVariable("idNote") Integer idNote, @PathVariable("idEvent") Integer idEvent) {
		eventService.removeNote(idNote, idEvent);
	}
	
	@DeleteMapping("/removeLike/{idEvent}/{idUser}")
	@ResponseBody
	public void removeLike(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.removeLikeEvent(idEvent, idUser);
	}
	
	@DeleteMapping("/uncommentEvent/{idComment}")
	@ResponseBody
	public void uncommentEvent(@PathVariable("idComment") Integer idComment) {
		eventService.uncommentEvent(idComment);
	}
	

	@PutMapping("/noteEvent/{idEvent}/{eventNote}/{idUser}")
	@ResponseBody
	public void noteEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("eventNote") EventNote eventNote, @PathVariable("idUser")Long idUser){
		eventService.noteEvent(idEvent, eventNote, idUser);
	}

	@PutMapping("/commentEvent/{idEvent}/{idUser}/{comment}")
	@ResponseBody
	public void commentEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser, @PathVariable("comment") String comment){
		eventService.commentEvent(idEvent, idUser, comment);
	}
	
	@PutMapping("/joinEvent/{idEvent}/{idUser}")
	@ResponseBody
	public void joinEvent(@PathVariable("idEvent") Integer idEvent,@PathVariable("idUser") Long idUser) {
		eventService.joinEvent(idEvent, idUser);
	}
	
	@PutMapping("/likeE/{idEvent}/{idUser}")
	@ResponseBody
	public void likeEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.likeEvent(idEvent, idUser);
	}	
	@PutMapping("/unjoinEvent/{idEvent}/{idUser}")
	@ResponseBody
	public void unjoinEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.unjoinEvent(idEvent, idUser);
	}
	
	@PutMapping("/AddEventToFav/{idEvent}/{idUser}")
	@ResponseBody
	public void AddEventToFav(@PathVariable("idEvent")Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.AddEventToFav(idEvent, idUser);
	}
	
	
	@GetMapping("/getUsersByComment/{idEvent}")
	@ResponseBody
	public List<User> getUsersByComment(@PathVariable("idEvent") Integer idEevent){
		return eventService.getUsersByComment(idEevent);
	}
	
	@GetMapping("/getUsersByLikes/{idEvent}")
	@ResponseBody
	public List<User> getUsersByLikes(@PathVariable("idEvent") Integer idEevent){
		return eventService.getUsersByLikes(idEevent);
	}
	
	@GetMapping("/getUsersByCentreInterest/{idUser}")
	@ResponseBody
	List<User> getUsersByCI(@PathVariable("idUser")Long idUser){
		return eventService.getUsersByCI(idUser);
	}
	
	@GetMapping("/listOfEvent")
	@ResponseBody
	public List<Event> listOfEvent(){
		return eventService.listOfEvent();
	}
	
	@GetMapping("/getEventsByOffice/{idOffice}")
	@ResponseBody
	List<Event> getEventByOffice(@PathVariable("idOffice") Integer idOffice){
		return eventService.getEventByOffice(idOffice);
	}
	
	
	/*@PutMapping("/uploadImage")
	public String uploadImage(MultipartFile imageFile ) {
		String returnValue = "start";
		try {
			eventService.saveImage(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("erro saving photo !", e);
			return returnValue = "error";
		}
		return returnValue;
		
	}*/

	
}
