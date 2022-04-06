package com.springheroes.wellco.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springheroes.wellco.enumeration.CentreInter;
import com.springheroes.wellco.enumeration.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
 //  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
   //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  
    private String password;
    private String email;
    @JsonIgnore
    private String profileImageUrl;
    @JsonIgnore
    private Date lastLoginDate;
    @JsonIgnore
    private Date lastLoginDateDisplay;
    @JsonIgnore
    private Date joinDate;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}
    @JsonIgnore
    private String[] authorities;
    @JsonIgnore
    private boolean isActive;
    @JsonIgnore
    private boolean isNotLocked;
    @JsonIgnore
    private int badWords;
    
    @ManyToMany(mappedBy = "users" , cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Event> events;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<CommentEvent> commentsE;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private CentreInter centreInter;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<CI> cI;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Favourite> favourites;

    

  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

 

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

}
