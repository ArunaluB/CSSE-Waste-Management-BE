package edu.sliit.repository;

import edu.sliit.document.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    List<Payment> findByUserId(String userId);

}
