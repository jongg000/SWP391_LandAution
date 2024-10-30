package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidDTO {
    private int bidId;
    private long bidAmount;
    private java.time.LocalDateTime bidTime;
    private int registrationId;
}
