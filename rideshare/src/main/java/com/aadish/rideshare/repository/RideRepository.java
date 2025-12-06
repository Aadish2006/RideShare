package com.aadish.rideshare.repository;

import com.aadish.rideshare.model.Ride;
import com.aadish.rideshare.model.RideStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {

    List<Ride> findByStatus(RideStatus status);

    List<Ride> findByUserId(String userId);
}