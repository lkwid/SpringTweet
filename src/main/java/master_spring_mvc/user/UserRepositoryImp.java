package master_spring_mvc.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import master_spring_mvc.error.EntityNotFoundException;

@Repository
public class UserRepositoryImp implements UserRepository {
	private final Map<String, User> userMap = new ConcurrentHashMap<>();

	@Override
	public User update(String email, User user) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("Użytkownik " + email + " nie istnieje");
		user.setEmail(email);
		return userMap.put(email, user);
	}

	@Override
	public User save(User user) {
		return userMap.put(user.getEmail(), user);
	}

	@Override
	public User findOne(String email) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("Użytkownik " + email + " nie istnieje");
		return userMap.get(email);
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<User>(userMap.values());
	}

	@Override
	public void delete(String email) throws EntityNotFoundException {
		if (!exists(email))
			throw new EntityNotFoundException("Użytkownik " + email + " nie istnieje");
		userMap.remove(email);
	}

	@Override
	public boolean exists(String email) {
		return userMap.containsKey(email);
	}

	@Override
	public void reset(User... users) {
		userMap.clear();
		for (User user : users) {
			save(user);
		}
	}

}
