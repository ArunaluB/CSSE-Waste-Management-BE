package edu.sliit.repository;

import edu.sliit.document.Collector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollecterRepository extends MongoRepository<Collector,String> {
    List<Collector> findAllByUserId(String userId);

}
