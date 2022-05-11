package com.springheroes.wellco.controllers;

import com.springheroes.wellco.entities.HttpResponse;
import com.springheroes.wellco.entities.User;
import com.springheroes.wellco.entities.UserPrincipal;
import com.springheroes.wellco.enumeration.CentreInter;
import com.springheroes.wellco.enumeration.Role;
import com.springheroes.wellco.exception.entities.*;
import com.springheroes.wellco.services.UserService;
import com.springheroes.wellco.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.springheroes.wellco.constant.FileConstant.*;
import static com.springheroes.wellco.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/", "/user"})
public class UserController extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent :";
    public static final String USER_DELETE_SUCCESSFULLY = "User delete successfully";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;




    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }



    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/register/{role}")
    public ResponseEntity<User> register(@RequestBody User user, @PathVariable("role") Role role) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), role);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
@PostMapping("/add/{role}")
public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false ) MultipartFile profileImage,@PathVariable("role") Role role) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
    User newUser = userService.addNewUser(firstName, lastName, username,email,Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage, role);
return new ResponseEntity<>(newUser, OK);
}

    @PostMapping("/update/{role}/{ci}")
    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @PathVariable("role") Role role,
                                           @PathVariable("ci") CentreInter ci,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
        User updatedUser = userService.updateUser(currentUsername, firstName, lastName, username,email, role, profileImage,ci);
        return new ResponseEntity<>(updatedUser, OK);
    }
    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        return  new ResponseEntity<>(user,OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<User>>getAllUsers(){
        List<User> users = userService.getUsers();
        return  new ResponseEntity<>(users,OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse>resetPassword(@PathVariable("email") String email) throws EmailNotFoundException, MessagingException {
       userService.resetPassword(email);
        return response (OK, EMAIL_SENT + email);
    }
@DeleteMapping("/delete/{id}")
@PreAuthorize("hasAnyAuthority('user:delete')")
public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return response(NO_CONTENT, USER_DELETE_SUCCESSFULLY);
}

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {
       User user = userService.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }
    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }
    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
return new  ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
           message.toUpperCase()), httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user){
       HttpHeaders headers = new HttpHeaders();
       headers.add(JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(user));
       return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    @PutMapping("/setCentreInteres/{idUser}/{centreIn}")
    @ResponseBody
    public void setCentreInterest(@PathVariable("idUser")Long idUSer, @PathVariable("centreIn")CentreInter centreIn) {
    	userService.setCentreInterest(idUSer, centreIn);
    }
}
