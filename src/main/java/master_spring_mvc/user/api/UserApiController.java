package master_spring_mvc.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import master_spring_mvc.error.EntityNotFoundException;
import master_spring_mvc.user.User;
import master_spring_mvc.user.UserRepository;

@RestController
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
public class UserApiController {
	private UserRepository userRepository;
	
	@Autowired
	public UserApiController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = HttpStatus.OK;
		if (!userRepository.exists(user.getEmail()))
			status = HttpStatus.CREATED;
		User saved = userRepository.save(user);
		return new ResponseEntity<>(saved, status);
	}
	
	@RequestMapping(value = "/users/{email}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException {
		User saved = userRepository.update(email, user);
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable String email) throws EntityNotFoundException {
		userRepository.delete(email);
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
}
