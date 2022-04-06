package com.springheroes.wellco.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.springheroes.wellco.enumeration.Offer;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCollaborator;
    private String name;
    private float rating;
    @Enumerated(EnumType.STRING)
    private CollabType collabType;
    @Enumerated(EnumType.STRING)
    private Offer offer;
}