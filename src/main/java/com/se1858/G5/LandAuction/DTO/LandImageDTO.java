package com.se1858.G5.LandAuction.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandImageDTO {
    private int imageId;
    private String name;
    private int landId;
    private String imageUrl;
}