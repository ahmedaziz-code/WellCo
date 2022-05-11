package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.User;
import com.springheroes.wellco.enumeration.CentreInter;
import com.springheroes.wellco.enumeration.Role;
import com.springheroes.wellco.exception.entities.EmailExistException;
import com.springheroes.wellco.exception.entities.EmailNotFoundException;
import com.springheroes.wellco.exception.entities.UserNotFoundException;
import com.springheroes.wellco.exception.entities.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {
	User register(String firstName, String lastName, String username, String email,Role role)
			throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException;

	List<User> getUsers();

	User findUserByUsername(String username);

	User findUserByEmail(String email);

	User addNewUser(String firstName, String lastName, String username, String email,  boolean isNonLocked,
			boolean isActive, MultipartFile profileImage, Role role)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

	User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, Role role, MultipartFile profileImage, CentreInter ci)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

	void deleteUser(long id);

	void resetPassword(String email) throws MessagingException, EmailNotFoundException;

	User updateProfileImage(String username, MultipartFile profileImage)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

	void setCentreInterest(Long idUser, CentreInter centreInter);

}
