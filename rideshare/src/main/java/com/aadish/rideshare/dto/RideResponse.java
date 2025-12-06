package com.aadish.rideshare.dto;

import com.aadish.rideshare.model.RideStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RideResponse {
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private RideStatus status;
    private Date createdAt;
}
