package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDTO {
    private int wishlistId;
    private int userId;
    private int auctionId;
}