package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDto {
    private int auctionId;
    private int landId;
    private String status;
    private Date startTime;
    private Date endTime;
    private Float highestBid;
}
