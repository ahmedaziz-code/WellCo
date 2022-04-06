package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.CI;
import com.springheroes.wellco.entities.User;
import com.springheroes.wellco.entities.UserPrincipal;
import com.springheroes.wellco.enumeration.CentreInter;
import com.springheroes.wellco.enumeration.Role;
import com.springheroes.wellco.exception.entities.EmailExistException;
import com.springheroes.wellco.exception.entities.EmailNotFoundException;
import com.springheroes.wellco.exception.entities.UserNotFoundException;
import com.springheroes.wellco.exception.entities.UsernameExistException;
import com.springheroes.wellco.repositories.CIRepo;
import com.springheroes.wellco.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.springheroes.wellco.constant.FileConstant.*;
import static com.springheroes.wellco.constant.UserImplConstant.DEFAULT_USER_IMAGE_PATH;
import static com.springheroes.wellco.constant.UserImplConstant.*;
import static com.springheroes.wellco.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	private EmailService emailService;
	
	@Autowired
	CIRepo ciRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		} else {
			validateLoginAttempt(user);
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_BY_USERNAME + username);
			return userPrincipal;
		}
	}

	@Override
	public User register(String firstName, String lastName, String username, String email, Role role )
			throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
		validateNewUsernameAndEmail(EMPTY, username, email);
		User user = new User();
		user.setUserId(generateUserId());
		String password = generatePassword();
		String encodedPassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodePassword(password));
		user.setActive(true);
		user.setRole(role);
		user.setNotLocked(true);
		user.setAuthorities(ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		LOGGER.info("New user password: " + password);
		emailService.sendNewPasswordEmail(firstName, password, email);
		return user;
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmail = findUserByEmail(newEmail);
		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);
			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}
			if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		} else {
			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmail != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return null;
		}

	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User addNewUser(String firstName, String lastName, String username, String email,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage, Role role)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
		validateNewUsernameAndEmail(EMPTY, username, email);
		User user = new User();
		String password = generatePassword();
		user.setUserId(generateUserId());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setJoinDate(new Date());
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(encodePassword(password));
		user.setActive(isActive);
		user.setNotLocked(isNonLocked);
		user.setRole(role);
		user.setAuthorities(role.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		saveProfileImage(user, profileImage);
		LOGGER.info("New user password: " + password);
		return user;

	}

	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
		if (profileImage != null) {
			Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
			if (!Files.exists(userFolder)) {
				Files.createDirectories(userFolder);
				LOGGER.info(DIRECTORY_CREATED);

			}
			Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
			Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION),
					REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
			userRepository.save(user);
			LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
		}
	}

	@Override
	public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, Role role, MultipartFile profileImage, CentreInter ci)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
		User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
		currentUser.setFirstName(newFirstName);
		currentUser.setLastName(newLastName);
		currentUser.setUsername(newUsername);
		currentUser.setEmail(newEmail);
		currentUser.setRole(role);
		currentUser.setAuthorities(role.getAuthorities());
		currentUser.setCentreInter(ci);
		userRepository.save(currentUser);
		saveProfileImage(currentUser, profileImage);
		// LOGGER.info("New user password: " + password);
		return currentUser;
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);

	}

	@Override
	public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
		}
		String password = generatePassword();
		user.setPassword(encodePassword(password));
		userRepository.save(user);
		emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
	}

	@Override
	public User updateProfileImage(String username, MultipartFile profileImage)
			throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
		User user = validateNewUsernameAndEmail(username, null, null);
		saveProfileImage(user, profileImage);
		return user;
	}

	private String setProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	private String getTemporaryProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username)
				.toUriString();
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}
	
	@Override
	public void setCentreInterest(Long idUser, CentreInter centreInter) {
		User u = userRepository.findById(idUser).get();
		CI ci = new CI();
		ci.setCentreInterest(centreInter.toString());
		ci.setUser(u);
		ciRepo.save(ci);
		userRepository.save(u);
		
	}

}
