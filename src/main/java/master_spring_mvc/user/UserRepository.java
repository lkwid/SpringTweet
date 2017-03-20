package master_spring_mvc.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import master_spring_mvc.error.EntityNotFoundException;

@Repository
public class UserRepository {
	private final Map<String, User> userMap = new ConcurrentHashMap<>();
	
	public User update(String email, User user) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("U¿ytkownik " + email + " nie istnieje");
		user.setEmail(email);
		return userMap.put(email, user);
	}
	
	public User save(User user) {
		return userMap.put(user.getEmail(), user);
	}
	
	public User findOne(String email) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("U¿ytkownik " + email + " nie istnieje");
		return userMap.get(email);
	}
	
	public List<User> findAll() {
		return new ArrayList<User>(userMap.values());
	}
	
	public void delete(String email) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("U¿ytkownik " + email + " nie istnieje");
		userMap.remove(email);
	}
	
	public boolean exists(String email) {
		return userMap.containsKey(email);
	}
	
}
