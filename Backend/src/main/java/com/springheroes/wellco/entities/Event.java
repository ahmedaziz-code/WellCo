package com.springheroes.wellco.entities;

import java.io.Serializable;
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
	private String startDate;
	private String endDate;
	private int attendsNumber;
	private String tags;
	private String FileName;
	private String lat;
	private String lng;
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@JsonIgnore
	@OneToOne
	private Image file;


	@Enumerated(EnumType.STRING)
	private EventNote eventNote;


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


}
