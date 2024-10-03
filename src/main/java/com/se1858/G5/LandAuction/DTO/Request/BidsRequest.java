package com.se1858.G5.LandAuction.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidsRequest {
    private int bidId;
    private int auctionRegistrationId;
    private float bidAmount;
    private Date bidTime;
}

