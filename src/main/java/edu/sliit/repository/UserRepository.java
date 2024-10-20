package edu.sliit.repository;

import edu.sliit.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  UserRepository extends MongoRepository<User,String> {
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByUsername( String userId);
}
