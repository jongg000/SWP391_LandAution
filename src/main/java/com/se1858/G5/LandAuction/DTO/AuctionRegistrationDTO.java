package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionRegistrationDTO {
    private int registrationId;
    private int auctionId;
    private int userId;
}
