package com.aadish.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    private String id;

    private String userId;    // passenger

    private String driverId;  // driver (nullable until accepted)

    private String pickupLocation;

    private String dropLocation;

    private RideStatus status;

    private Date createdAt;
}
