package master_spring_mvc.user;

import java.util.List;

import master_spring_mvc.error.EntityNotFoundException;

public interface UserRepository {

	User update(String email, User user) throws EntityNotFoundException;

	User save(User user);

	User findOne(String email) throws EntityNotFoundException;

	List<User> findAll();

	void delete(String email) throws EntityNotFoundException;

	boolean exists(String email);

	void reset(User... users);

}