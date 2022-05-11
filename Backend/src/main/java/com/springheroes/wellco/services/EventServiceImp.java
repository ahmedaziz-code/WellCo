package com.springheroes.wellco.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.springheroes.wellco.entities.*;
import com.springheroes.wellco.entities.Event;
import com.springheroes.wellco.enumeration.*;
import com.springheroes.wellco.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImp implements IEventService {

	@Autowired
	EventRepository eventRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentEventRepository commentRepository;
	@Autowired
	NoteERepository noteERepository;
	@Autowired
	LikeEventRepo lEventRepo;
	@Autowired
	OfficeRepo officeRepo;
	@Autowired
	FavouriteRepo favouriteRepo;
	@Autowired
	ImageRepo imageRepo;



	/******************************************************************/



	/* ADD EVENT */
	@Override
	public Event addEvent(Event e, Integer idOffice) {
		Office off = officeRepo.findById(idOffice).get();
		Set<Office> office = new HashSet<Office>();
		office.add(off);
		e.setOffices(office);
		return eventRepository.save(e);
	}

	@Override
	public Event getEvent(Integer idEvent) {
		return eventRepository.findById(idEvent).get();
	}

	@Override
	public Event findById(Integer idEvent) {
		return eventRepository.findById(idEvent).get();
	}


	/*ADD OFFICE*/
	@Override
	public Office addOffice(Office off) {
		return officeRepo.save(off);
	}

	/* DELETE EVENT */
	@Override
	public void deletEvent(int idEvent) {
		eventRepository.delete(eventRepository.findById(idEvent).get());

	}

	/* lIST EVENT */
	@Override
	public List<Event> listOfEvent() {

		return eventRepository.findAll();
	}

	/* NOTE EVENT */
	@Override
	public void noteEvent(Integer idEvent, EventNote eventNote, Long idUser) {
		
		Event e = eventRepository.findById(idEvent).get();
		User u = userRepository.findById(idUser).get();
		List<NoteE> ln = noteERepository.findByUserAndEvent(u,e);
		if(ln.size()==0) {
			NoteE n = new NoteE();
			e.setEventNote(setEventNote(eventNote, e));
			n.setNote(setNotes(eventNote));
			n.setUser(u);
			n.setEvent(e);
			noteERepository.save(n);
			eventRepository.save(e);
		}
	
	}
	
	/* REMOVE NOTE */
	@Override
	public void removeNote(Integer idNote, Integer idEvent) {
		EventNote finalN,n = null;
		NoteE note = noteERepository.findById(idNote).get();
		Event e = eventRepository.findById(idEvent).get();
		if(note.getNote()==1) {
			finalN = n.Bad;
		}
		else if(note.getNote()==2 ) {
			finalN = n.NotBad;
		}
		else if(note.getNote()==3 ) {
			finalN = n.Good;
		}
		else if(note.getNote()==4 ) {
			finalN = n.VerryGood;
		}
		else {
			finalN = n.Excellent;
		}	
		if(e.getNotes().size()==1 || e.getNotes().size()==0) {
			e.setEventNote(null);
		}
		else {
			e.setEventNote(deleteNote(e, finalN ));
		}
		
		
		eventRepository.save(e);
		noteERepository.delete(note);
		
	}

	/* COMMENT EVENT */
	@Override
	public void commentEvent(Integer idEvent, Long idUser, String comment) {

		User u = userRepository.findById(idUser).get();
		if(u.getBadWords()<=3) {
			}
			String com = verifyComment(comment); 
			if(!comment.equals(com)) {
				u.setBadWords(u.getBadWords()+1);
			}
			Event e = eventRepository.findById(idEvent).get();
			CommentEvent co = new CommentEvent();
			Date date = new Date(System.currentTimeMillis());
			co.setCreatedDate(date.toString());
			co.setEvent(e);
			co.setUser(u);
			co.setContent(com);
			commentRepository.save(co);
		}

	
	/*UNCOMMENT EVENT*/
	@Override
	public void uncommentEvent(Integer idComment) {
		commentRepository.deleteById(idComment);
	}
	
	/*USERS BY COMMENT*/
	@Override
	public List<User> getUsersByComment(Integer idEevent){
		Event ev = eventRepository.findById(idEevent).get();
		List<CommentEvent> le = commentRepository.findByEvent(ev);
		List<User> lu = new ArrayList<User>();
		for(CommentEvent c : le) {
				lu.addAll(c.getEvent().getUsers());
		}
		for(int i=0; i<lu.size(); i++) {
			for(int j=1; j<lu.size(); j++) {
				if(lu.get(i).getId() == lu.get(j).getId()) {
					lu.remove(j);
				}
			}
		}
		return lu;
	}
	

	/*LIKE EVENT*/
	@Override
	public void likeEvent(Integer idEvent, Long idUser) {
		
		Event e = eventRepository.findById(idEvent).get();
		User u = userRepository.findById(idUser).get();
		List<LikeEvent> lk = lEventRepo.findByUser(u);
		log.info("likes : " + lk.size());

			LikeEvent l = new LikeEvent();
			l.setEvent(e);
			l.setUser(u);
			
			lEventRepo.save(l);
			int likes = e.getLikes()+1;
			e.setLikes(likes);
			eventRepository.save(e);


	}

	
	/*USERS BY LIKES*/
	@Override
	public List<User> getUsersByLikes(Integer IdEvent){
		Event e = eventRepository.findById(IdEvent).get();
		List<LikeEvent> le = lEventRepo.findByEvent(e);
		List<User> lu = new ArrayList<User>();
		for(LikeEvent l : le) {
				lu.add(l.getUser());
		}
		return lu;
	}

	/*REMOVE LIKE*/
	@Override
	public void removeLikeEvent(Integer idEvent, Long idUser) {
		Event e = eventRepository.findById(idEvent).get();
		List<LikeEvent> le = lEventRepo.findByEvent(e);
		for(LikeEvent l : le) {
			if(l.getUser().getId() == idUser) {
				e.setLikes(e.getLikes() -1);
				lEventRepo.deleteById(l.getIdLike());
				eventRepository.save(e);
			}
		}
		
	}
	
	/* JOIN EVENT */
	@Override
	public void joinEvent(Integer idEvent, Long idUser) {
		Event e = eventRepository.findById(idEvent).get();
		int nbP = e.getAttendsNumber();
		if (nbP > 0) {
			e.getUsers().add(userRepository.findById(idUser).get());
			e.setAttendsNumber(nbP - 1);
			eventRepository.save(e);
		}

	}
	
	/*UNJOIN EVENT*/
	@Override
	public void unjoinEvent(Integer idEvent, Long idUser) {
		Event e = eventRepository.findById(idEvent).get();
		for(User u : e.getUsers()) {
			if (u.getId() == idUser) {
				e.getUsers().remove(u);
				int nb = e.getAttendsNumber();
				e.setAttendsNumber(nb + 1);
				eventRepository.save(e);
			}
		}
		
	}
	
	/*USERS BY CENTRE OF INTEREST*/
	@Override
	public List<User> getUsersByCI(Long idUser) {
		List<User> luFinal = new ArrayList<User>();
		User u = userRepository.findById(idUser).get();
		List<User> lu = userRepository.findAll();
		for(int i=0; i<lu.size(); i++) {
			if (lu.get(i).getId()==u.getId()) {
				lu.remove(lu.get(i));
			}
		}
		for(User user : lu) {
			for(CI ci : user.getCI()) {
				for(CI ciu : u.getCI()) {
					if(ciu.getCentreInterest().equals(ci.getCentreInterest())) {
						luFinal.add(user);
					}
				}
			}
		}
		for(int i=0; i<luFinal.size(); i++) {
			for(int j=1; j<luFinal.size(); j++) {
				if(luFinal.get(i).getId() == luFinal.get(j).getId()) {
					luFinal.remove(j);
				}
			}
		}
		return luFinal;
	}
	
	/*EVENTS BY OFFICE*/
	@Override
	public List<Event> getEventByOffice(Integer idOffice) {
		Office off = officeRepo.findById(idOffice).get();
		List<Event> le = eventRepository.findByOffices(off);
		return le;
	}
	
	/*ADD EVENT TO FAVOURITE*/
	@Override
	public void AddEventToFav(Integer idEvent, Long idUser) {
		Event e = eventRepository.findById(idEvent).get();
		User u = userRepository.findById(idUser).get();
		Favourite fav = new Favourite();
		fav.setEvent(e);
		fav.setUser(u);
		favouriteRepo.save(fav);
	}

	@Override
	public List<CommentEvent> getCommentByEvent(Integer idEvent) {

		Event e = eventRepository.findById(idEvent).get();
		List<CommentEvent> lc= commentRepository.findByEvent(e);
		return lc;
	}

	@Override
	public Image saveImage(Image i) {
		return imageRepo.save(i);
	}

	@Override
	public Image getFile(Integer fileId) {
		return imageRepo.findById(fileId).get();

	}

	/*GET OFFICES*/
	@Override
	public List<Office> getAllOffices() {
		return officeRepo.findAll();
	}

	
	/******************************************************************/
	
	@Scheduled(cron = "* * * 1 * *")
	public void badWordSetTo0() {
		List<User> lu = userRepository.findAll();
		for(User u : lu) {
			u.setBadWords(0);
		}
	}
	
	/******************************************************************/
	
	public String verifyComment(String comment) {
			
			String[] badWords = {"fuck", "idiot","shit", "dick",
					"asshole", "bitch", "bastard", "damn"};
			String newComment ="", newWord = "";
			String[] words = comment.split(" ");
			for(String word : words) {
				int a =0;
				int j = 0;
				for(String bad : badWords) {
					j++;
					if(word.indexOf(bad) == -1) {
						if(j==badWords.length && a ==0) {
							newComment += word + " ";
							j=0;
						}
					}
					else if(word.indexOf(bad)!= -1) {
						a++;
						int x = word.indexOf(bad);
						log.info(""+x);
						for(int i=0 ; i<word.length() ; i++) {
							if(i <= x || i == x+bad.length()-1 || i >= x+bad.length()) { 
								newWord += String.valueOf(word.charAt(i));
						}
							else {
								newWord += "*";
							}
					}
						newComment += newWord + " ";
						newWord = "";
						j=0;	
					}
					
				}
			}
			log.info(newComment);
			return newComment;
		}
	
	
	
	public EventNote setEventNote(EventNote n, Event e) {
		EventNote finalN ;
		Set<NoteE> ln = e.getNotes();
		int notes = 0;
		float notesMoy = 0;
			for(NoteE note : ln) {
				notes += note.getNote();
			}
			if(n.toString().equals("Bad")) {
				notes += 1;
			}
			else if(n.toString().equals("NotBad")) {
				notes += 2;
			}
			else if(n.toString().equals("Good")) {
				notes += 3;
			}
			else if (n.toString().equals("VerryGood")) {
				notes += 4;
			}
			else {
				notes += 5;
			}
			
		notesMoy = notes/(ln.size()+1);
		if(notesMoy<=1 && notesMoy>=0 ) {
			finalN = n.Bad;
		}
		else if(notesMoy<=2 && notesMoy>=1 ) {
			finalN = n.NotBad;
		}
		else if(notesMoy<=3 && notesMoy>=2 ) {
			finalN = n.Good;
		}
		else if(notesMoy<=4 && notesMoy>=3 ) {
			finalN = n.VerryGood;
		}
		else {
			finalN = n.Excellent;
		}	
		
		return finalN;
	}
	
	public int setNotes(EventNote n) {

		int notes = 0;
		if(n.toString().equals("Bad")) {
			notes += 1;
		}
		else if(n.toString().equals("NotBad")) {
			notes += 2;
		}
		else if(n.toString().equals("Good")) {
			notes += 3;
		}
		else if (n.toString().equals("VerryGood")) {
			notes += 4;
		}
		else {
			notes += 5;
		}
		return notes;
	}
	
	public EventNote deleteNote(Event e, EventNote n) {
		int notes = 0;
		float notesMoy = 0;
		EventNote finalN;
		for (NoteE note : e.getNotes()) {
			notes += note.getNote();
		}
		if(n.toString().equals("Bad")) {
			notes-=1;
		}
		else if(n.toString().equals("NotBad")) {
			notes -= 2;
		}
		else if(n.toString().equals("Good")) {
			notes -= 3;
		}
		else if (n.toString().equals("VerryGood")) {
			notes -= 4;
		}
		else {
			notes -= 5;
		}
		notesMoy = notes/(e.getNotes().size());
		if(notesMoy<=1 && notesMoy>=0 ) {
			finalN = n.Bad;
		}
		else if(notesMoy<=2 && notesMoy>=1 ) {
			finalN = n.NotBad;
		}
		else if(notesMoy<=3 && notesMoy>=2 ) {
			finalN = n.Good;
		}
		else if(notesMoy<=4 && notesMoy>=3 ) {
			finalN = n.VerryGood;
		}
		else {
			finalN = n.Excellent;
		}	
		
		return finalN;
	}


	

}
