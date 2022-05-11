package com.springheroes.wellco.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springheroes.wellco.enumeration.*;
import com.springheroes.wellco.entities.*;
import com.springheroes.wellco.repositories.EventRepository;
import com.springheroes.wellco.repositories.ImageRepo;
import com.springheroes.wellco.repositories.OfficeRepo;
import com.springheroes.wellco.services.FileStorageService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springheroes.wellco.services.IEventService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;


@RestController
@RequestMapping("/Event")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class EventController {
	
	@Autowired
	IEventService eventService;
	EventRepository eventRepository;
	OfficeRepo officeRepo;
	@Autowired
	ImageRepo imageRepo;
	@Autowired ServletContext context;


//	public static String uploadDirectory = System.getProperty("user.dir")+"\\uploads\\";
	ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	//http://localhost:8089/WellCo/Event/addEvent/{idOffice}
	@PostMapping(value="/addEvent/{idOffice}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Event> addEvent(@RequestPart("event") String event,@PathVariable("idOffice")Integer idOffice, @RequestPart("file") MultipartFile file) throws IOException {

		boolean isExit = new File(context.getRealPath("/Images/")).exists();
		if (!isExit)
		{
			new File (context.getRealPath("/Images/")).mkdir();
			System.out.println("mk dir.............");
		}
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
		try
		{
			System.out.println("Image");
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

		}catch(Exception e) {
			e.printStackTrace();
		}

		Image NeventImage = new Image();
		NeventImage.setImageUrl(context.getRealPath("Images/"+File.separator+newFileName));
		NeventImage.setData(file.getBytes());
		NeventImage.setName(file.getOriginalFilename());
		NeventImage.setType(file.getContentType());
		Image i = eventService.saveImage(NeventImage);
		Event Nevent = objectMapper.readValue(event, Event.class);;
		Nevent.setFile(i);
		Nevent.setFileName(file.getOriginalFilename());
		eventService.addEvent(Nevent,idOffice);
		return new ResponseEntity<>(Nevent, HttpStatus.CREATED);
	}

	
	//http://localhost:8089/WellCo/Event/addOffice
	@PostMapping("/addOffice")
	@ResponseBody
	public Office addOffice(@RequestBody Office off) {
		return eventService.addOffice(off);
	}
	
	//http://localhost:8089/WellCo/Event/deleteEvent/{idEvent}
	@DeleteMapping("/deleteEvent/{idEvent}")
	@ResponseBody
	public void deleteEvent(@PathVariable("idEvent") Integer idEvent) {
		eventService.deletEvent(idEvent);
	}

	//http://localhost:8089/WellCo/Event/deleteNote/{idNote}/{idEvent}
	@DeleteMapping("/deleteNote/{idNote}/{idEvent}")
	 @ResponseBody
	 public void removeNote(@PathVariable("idNote") Integer idNote, @PathVariable("idEvent") Integer idEvent) {
		eventService.removeNote(idNote, idEvent);
	}
	//http://localhost:8089/WellCo/Event/removeLike/{idEvent}/{idUser}
	@DeleteMapping("/removeLike/{idEvent}/{idUser}")
	@ResponseBody
	public void removeLike(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.removeLikeEvent(idEvent, idUser);
	}
	//http://localhost:8089/WellCo/Event/uncommentEvent/{idComment}
	@DeleteMapping("/uncommentEvent/{idComment}")
	@ResponseBody
	public void uncommentEvent(@PathVariable("idComment") Integer idComment) {
		eventService.uncommentEvent(idComment);
	}

	//http://localhost:8089/WellCo/Event/unjoinEvent/{idEvent}/{idUser}
	@DeleteMapping("/unjoinEvent/{idEvent}/{idUser}")
	@ResponseBody
	public void unjoinEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.unjoinEvent(idEvent, idUser);
	}
	
//http://localhost:8089/WellCo/Event/noteEvent/{idEvent}/{eventNote}/{idUser}
	@PutMapping("/noteEvent/{idEvent}/{eventNote}/{idUser}")
	@ResponseBody
	public void noteEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("eventNote") EventNote eventNote, @PathVariable("idUser")Long idUser){
		eventService.noteEvent(idEvent, eventNote, idUser);
	}
//http://localhost:8089/WellCo/Event/commentEvent/{idEvent}/{idUser}/{comment}
	@PutMapping("/commentEvent/{idEvent}/{idUser}/{comment}")
	@ResponseBody
	public void commentEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser, @PathVariable("comment") String comment){
		eventService.commentEvent(idEvent, idUser, comment);
	}
	//http://localhost:8089/WellCo/Event/joinEvent/{idEvent}/{idUser}
	@PutMapping("/joinEvent/{idEvent}/{idUser}")
	@ResponseBody
	public void joinEvent(@PathVariable("idEvent") Integer idEvent,@PathVariable("idUser") Long idUser) {
		eventService.joinEvent(idEvent, idUser);
	}
	//http://localhost:8089/WellCo/Event/likeE/{idEvent}/{idUser}
	@PutMapping("/likeE/{idEvent}/{idUser}")
	@ResponseBody
	public void likeEvent(@PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.likeEvent(idEvent, idUser);
	}

	//http://localhost:8089/WellCo/Event/AddEventToFav/{idEvent}/{idUser}
	@PutMapping("/AddEventToFav/{idEvent}/{idUser}")
	@ResponseBody
	public void AddEventToFav(@PathVariable("idEvent")Integer idEvent, @PathVariable("idUser") Long idUser) {
		eventService.AddEventToFav(idEvent, idUser);
	}
	
	//http://localhost:8089/WellCo/Event/getUsersByComment/{idEvent}
	@GetMapping("/getUsersByComment/{idEvent}")
	@ResponseBody
	public List<User> getUsersByComment(@PathVariable("idEvent") Integer idEevent){
		return eventService.getUsersByComment(idEevent);
	}
	//http://localhost:8089/WellCo/Event/getUsersByLikes/{idEvent}
	@GetMapping("/getUsersByLikes/{idEvent}")
	@ResponseBody
	public List<User> getUsersByLikes(@PathVariable("idEvent") Integer idEevent){
		return eventService.getUsersByLikes(idEevent);
	}
	//http://localhost:8089/WellCo/Event/getUsersByCentreInterest/{idUser}
	@GetMapping("/getUsersByCentreInterest/{idUser}")
	@ResponseBody
	List<User> getUsersByCI(@PathVariable("idUser")Long idUser){
		return eventService.getUsersByCI(idUser);
	}
	//http://localhost:8089/WellCo/Event/listOfEvent
	@GetMapping("/listOfEvent")
	@ResponseBody
	public List<Event> listOfEvent(){
		return eventService.listOfEvent();
	}
	//http://localhost:8089/WellCo/Event/getEventsByOffice/{idOffice}
	@GetMapping("/getEventsByOffice/{idOffice}")
	@ResponseBody
	List<Event> getEventByOffice(@PathVariable("idOffice") Integer idOffice){
		return eventService.getEventByOffice(idOffice);
	}
	//http://localhost:8089/WellCo/Event/listOffice
	@GetMapping("/listOffice")
	@ResponseBody
	public List<Office> listOfOffices(){
		return eventService.getAllOffices();
	}

	@GetMapping(path="/ImageEvent/{id}")
	public byte[] getPhoto(@PathVariable("id") Integer id) throws Exception{
		Event e = eventService.findById(id);
		return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+e.getFileName()));
	}

	@GetMapping("/comments/{idEvent}")
	public List<CommentEvent> getCommentByEvent(@PathVariable("idEvent")Integer idEvent) {
		return eventService.getCommentByEvent(idEvent);
	}

	@GetMapping("/event/{idEvent}")
	public Event getEvent(@PathVariable("idEvent") Integer idEvent){
		return eventService.getEvent(idEvent);
	}

	
}
