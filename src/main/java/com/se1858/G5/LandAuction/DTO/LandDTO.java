package com.se1858.G5.LandAuction.DTO;

import com.se1858.G5.LandAuction.Entity.LandImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LandDTO {
    private String landName;
    private String province;
    private String district;
    private String ward;
    private String location;
    private String description;
    private Float price;
    private String contact;
    private MultipartFile document;
    private List<MultipartFile> images;
    private LandImage landImage;
}
