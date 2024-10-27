package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandDTO {
    private int landId;
    private String description;
    private String name;
    private String province;
    private String district;
    private String ward;
    private int userId;
    private String location;
    private long price;
    private String contact;
    private String path;
}