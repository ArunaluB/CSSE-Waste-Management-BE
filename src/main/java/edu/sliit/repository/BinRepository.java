package edu.sliit.repository;

import edu.sliit.document.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinRepository extends MongoRepository<Bin,String> {
    List<Bin> findAllByUserId(String userId);
}
