package com.springheroes.wellco.controllers;

import com.springheroes.wellco.entities.Collaborator;
import com.springheroes.wellco.exception.entities.UserNotFoundException;
import com.springheroes.wellco.repositories.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "localhost:8083")
@RestController
@RequestMapping("/api/v1")
public class CollaboratorController {
    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @GetMapping("/collaborators")
    public List<Collaborator> getAllCollaborators() {
        return collaboratorRepository.findAll();
    }

    @GetMapping("/collaborators/{id}")
    public ResponseEntity<Collaborator> getCollaboratorById(@PathVariable(value = "id") Integer collaboratorId) throws UserNotFoundException {
        Collaborator collaborator = collaboratorRepository.findById(collaboratorId).orElseThrow(() -> new UserNotFoundException("Collaborator not found for this id :: " + collaboratorId));
        return ResponseEntity.ok().body(collaborator);
    }

    @PostMapping("/collaborators")
    public Collaborator createCollaborator(@Valid @RequestBody Collaborator collaborator) {
        return collaboratorRepository.save(collaborator);

    }

    @PutMapping("/collaborators/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@PathVariable(value = "id") Integer collaboratorId,
                                                           @Valid @RequestBody Collaborator collaboratorDetails) throws UserNotFoundException {
        Collaborator collaborator = collaboratorRepository.findById(collaboratorId).orElseThrow(() -> new UserNotFoundException("Collaborator not found for this id :: " + collaboratorId));
        collaborator.setName(collaboratorDetails.getName());
        collaborator.setRating(collaboratorDetails.getRating());
        collaborator.setOffer(collaboratorDetails.getOffer());
        collaborator.setCollabType(collaboratorDetails.getCollabType());
        final Collaborator updatedCollaborator = collaboratorRepository.save(collaborator);
        return ResponseEntity.ok(updatedCollaborator);
    }
    @DeleteMapping("/collaborators/{id}")
    public Map<String, Boolean> deleteCollaborator(@PathVariable(value = "id") Integer collaboratorId) throws UserNotFoundException{
        Collaborator collaborator = collaboratorRepository.findById(collaboratorId).orElseThrow(() -> new UserNotFoundException("Collaborator not found for this id :: " + collaboratorId));
     collaboratorRepository.delete(collaborator);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;

    }



}
