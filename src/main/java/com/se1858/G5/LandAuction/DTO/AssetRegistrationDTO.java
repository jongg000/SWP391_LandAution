package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetRegistrationDTO {
    private String Province;
    private String District;
    private String Ward;
    private String Location;
    private String Description;
    private Double price;
    private String contact;
    private int userid;
    private MultipartFile document;
    private List<MultipartFile> images;
}
