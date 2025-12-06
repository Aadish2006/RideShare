package com.aadish.rideshare.controller;

import com.aadish.rideshare.dto.CreateRideRequest;
import com.aadish.rideshare.dto.RideResponse;
import com.aadish.rideshare.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    // API point for USER: POST /api/v1/rides
    @PostMapping("/rides")
    public RideResponse createRide(@Valid @RequestBody CreateRideRequest req,
                                   Authentication authentication) {
        return rideService.createRide(req, authentication);
    }

    // DRIVER: GET /api/v1/driver/rides/requests
    @GetMapping("/driver/rides/requests")
    public List<RideResponse> getPendingRides(Authentication authentication) {
        return rideService.getPendingRequests(authentication);
    }

    // DRIVER: POST /api/v1/driver/rides/{rideId}/accept
    @PostMapping("/driver/rides/{rideId}/accept")
    public RideResponse acceptRide(@PathVariable String rideId,
                                   Authentication authentication) {
        return rideService.acceptRide(rideId, authentication);
    }

    // USER/DRIVER: POST /api/v1/rides/{rideId}/complete
    @PostMapping("/rides/{rideId}/complete")
    public RideResponse completeRide(@PathVariable String rideId,
                                     Authentication authentication) {
        return rideService.completeRide(rideId, authentication);
    }

    // USER: GET /api/v1/user/rides
    @GetMapping("/user/rides")
    public List<RideResponse> getUserRides(Authentication authentication) {
        return rideService.getUserRides(authentication);
    }
}
