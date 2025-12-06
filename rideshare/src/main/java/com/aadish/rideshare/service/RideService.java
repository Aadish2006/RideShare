package com.aadish.rideshare.service;

import com.aadish.rideshare.dto.CreateRideRequest;
import com.aadish.rideshare.dto.RideResponse;
import com.aadish.rideshare.exception.BadRequestException;
import com.aadish.rideshare.exception.NotFoundException;
import com.aadish.rideshare.model.Ride;
import com.aadish.rideshare.model.RideStatus;
import com.aadish.rideshare.model.User;
import com.aadish.rideshare.repository.RideRepository;
import com.aadish.rideshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private RideResponse toResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .userId(ride.getUserId())
                .driverId(ride.getDriverId())
                .pickupLocation(ride.getPickupLocation())
                .dropLocation(ride.getDropLocation())
                .status(ride.getStatus())
                .createdAt(ride.getCreatedAt())
                .build();
    }

    // USER: POST /api/v1/rides
    public RideResponse createRide(CreateRideRequest req, Authentication auth) {
        User user = getCurrentUser(auth);
        if (!"ROLE_USER".equals(user.getRole())) {
            throw new BadRequestException("Only USER can request rides");
        }

        Ride ride = Ride.builder()
                .userId(user.getId())
                .driverId(null)
                .pickupLocation(req.getPickupLocation())
                .dropLocation(req.getDropLocation())
                .status(RideStatus.REQUESTED)
                .createdAt(new Date())
                .build();

        return toResponse(rideRepository.save(ride));
    }

    // DRIVER: GET /api/v1/driver/rides/requests
    public List<RideResponse> getPendingRequests(Authentication auth) {
        User driver = getCurrentUser(auth);
        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can view pending rides");
        }
        return rideRepository.findByStatus(RideStatus.REQUESTED)
                .stream().map(this::toResponse).toList();
    }

    // DRIVER: POST /api/v1/driver/rides/{rideId}/accept
    public RideResponse acceptRide(String rideId, Authentication auth) {
        User driver = getCurrentUser(auth);
        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new BadRequestException("Ride is not in REQUESTED state");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus(RideStatus.ACCEPTED);

        return toResponse(rideRepository.save(ride));
    }

    // USER/DRIVER: POST /api/v1/rides/{rideId}/complete
    public RideResponse completeRide(String rideId, Authentication auth) {
        User current = getCurrentUser(auth);

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new BadRequestException("Ride must be ACCEPTED to complete");
        }

        // Optional: check if current user is passenger or driver of this ride
        if (!current.getId().equals(ride.getUserId())
                && (ride.getDriverId() == null ||
                !current.getId().equals(ride.getDriverId()))) {
            throw new BadRequestException("You are not part of this ride");
        }

        ride.setStatus(RideStatus.COMPLETED);
        return toResponse(rideRepository.save(ride));
    }

    // USER: GET /api/v1/user/rides
    public List<RideResponse> getUserRides(Authentication auth) {
        User user = getCurrentUser(auth);
        return rideRepository.findByUserId(user.getId())
                .stream().map(this::toResponse).toList();
    }
}
