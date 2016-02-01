package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.janastu.heritageapp.domain.Authority;
import org.janastu.heritageapp.domain.User;
import org.janastu.heritageapp.domain.util.MResponseToken;
import org.janastu.heritageapp.domain.util.RestReturnCodes;
import org.janastu.heritageapp.repository.UserRepository;
import org.janastu.heritageapp.security.SecurityUtils;
import org.janastu.heritageapp.security.xauth.Token;
import org.janastu.heritageapp.service.MailService;
import org.janastu.heritageapp.service.UserService;
import org.janastu.heritageapp.web.rest.dto.KeyAndPasswordDTO;
import org.janastu.heritageapp.web.rest.dto.UserDTO;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    /**
     * POST  /register -> register the user.
     */
    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        return userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(),
                    userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail().toLowerCase(),
                    userDTO.getLangKey());
                    String baseUrl = request.getScheme() + // "http"
                    "://" +                                // "://"
                    request.getServerName() +              // "myhost"
                    ":" +                                  // ":"
                    request.getServerPort() +              // "80"
                    request.getContextPath();              // "/myContextPath" or "" if deployed in root context

                    mailService.sendActivationEmail(user, baseUrl);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
        );
    }

    
    @RequestMapping(value = "/registerForMobile",
            method = RequestMethod.POST)
        @Timed
        public MResponseToken registerAccountForMobile(  @RequestParam String username, @RequestParam String  password, @RequestParam String  emailId,@RequestParam String  residentstatus , @RequestParam String agestatus, @RequestParam String specialmessage, HttpServletRequest request) {
            
                    	UserDTO userDTO = new UserDTO();
                    	 log.debug( "registerAccountForMobile called" +username+ "email"+emailId +"password"+password +"specialmessage"+ specialmessage);
                   MResponseToken mToken = new MResponseToken( );	 
                    	 //check if username is valid 
                    	 //check if email id is unique and valid
                    	 //check if password is valid
                    	 //go to register or else send a bad token;
                    	 
                    Optional<User> user = userRepository.findOneByLogin(username);
                    if(user.isPresent())
                    {
                    	 log.debug( "username exists " );
                    	 mToken.setStatus("NOTOK");
                    	 mToken.setCode(RestReturnCodes.USER_ID_EXISTS );
                    	 mToken.setMessage("Username exists - Try a new user");
                    	 return mToken;
                    }
                    
                    Optional<User> user2 =  userRepository.findOneByEmail(emailId );
                    
                    if(user2.isPresent())
                    {
                     log.debug( "email exists " );
                   	 
                   	 mToken.setCode(RestReturnCodes.EMAIL_ID_EXISTS );
                   	 mToken.setStatus("NOTOK");
                   	 mToken.setMessage("Username exists - Try a new EmailId");
                     return mToken;
                    }
                    
                    Boolean residentStatus =false;
                    if(residentstatus.equalsIgnoreCase("true" ))
                    {
                    	residentStatus = true;
                    	log.debug( "residentStatus true " );
                    }
                    try{	
                    
					//user name and email ID are not unique - String ageStatus, Boolean residenStatus, String specialMessage
                    User createdUser = userService.createUserInformationHeritage(username, password,
                    		username, username, emailId,"EN", agestatus, residentStatus,specialmessage);
                    mToken.setStatus("OK");
                    mToken.setCode(RestReturnCodes.SUCCESS);
                  	mToken.setMessage("Username Created ");
                  	mToken.setUserId(createdUser.getId());
                    }catch(Exception e)
                    {
                    	 mToken.setCode(RestReturnCodes.EXCEPTION );
                       	 mToken.setStatus("NOTOK");
                       	 mToken.setMessage("EXCEPTION" + e.getMessage());
                         return mToken;
                    	
                    }
                    //mailService.sendActivationEmail(user, baseUrl);
                    return mToken;
              
          
        }
    /**
     * GET  /activate -> activate the registered user.
     */
    @RequestMapping(value = "/activate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account -> get the current user.
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account -> update the current user information.
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
            .findOneByLogin(SecurityUtils.getCurrentUser().getUsername())
            .map(u -> {
                userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey());
                return new ResponseEntity<String>(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /change_password -> changes the current user's password
     */
    @RequestMapping(value = "/account/change_password",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                String baseUrl = request.getScheme() +
                    "://" +
                    request.getServerName() +
                    ":" +
                    request.getServerPort() +
                    request.getContextPath();
                mailService.sendPasswordResetMail(user, baseUrl);
                return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= UserDTO.PASSWORD_MIN_LENGTH &&
            password.length() <= UserDTO.PASSWORD_MAX_LENGTH);
    }
}
