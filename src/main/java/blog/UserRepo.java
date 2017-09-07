package blog;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<User, String> {
    public User findByUsername(String username);
    public User findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
}
