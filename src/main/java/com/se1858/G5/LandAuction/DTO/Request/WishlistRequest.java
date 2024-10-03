package com.se1858.G5.LandAuction.DTO.Request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistRequest {
    private int wishlistId;
    private int userId;
    private int auctionId;
}
