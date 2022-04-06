package com.springheroes.wellco.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springheroes.wellco.enumeration.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Integer idEvent;

	private String name;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	private String place;
	private int attendsNumber;
	private String tags;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private EventNote eventNote;

	@JsonIgnore()
	private int likes;


	@JsonIgnore
	@ManyToMany
	/*@JoinTable(name = "event_users",
			joinColumns=@JoinColumn(name="events_id_event", referencedColumnName="idEvent"),
			inverseJoinColumns=@JoinColumn(name="users_id_user", referencedColumnName="idUser"))*/
	private Set<User> users;
	

	@JsonIgnore
	@ManyToMany
	private Set<Collaborator> collaborators;
	
	@JsonIgnore
	@OneToMany(mappedBy = "event")
	private Set<NoteE> notes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "event")
	private Set<LikeEvent> likeEvent;
	
	@JsonIgnore
	@ManyToMany
	private Set<Office> offices;
	
	
	
	
	
	/*@JsonIgnore
	@Column(nullable = false)
	private String mainImage;
	
	@JsonIgnore
	@OneToMany(mappedBy = "event" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EventImage> images = new HashSet<>();
	
	public void addExtratImages(String imageName) {
		this.images.add(new EventImage(imageName, this)); 
	}*/

}
