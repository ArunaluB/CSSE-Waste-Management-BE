package edu.sliit.repository;

import edu.sliit.document.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    Optional<Schedule> findByScheduleId(String scheduleId);
}
