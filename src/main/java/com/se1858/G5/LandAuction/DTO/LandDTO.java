package com.se1858.G5.LandAuction.DTO;

import com.se1858.G5.LandAuction.Entity.LandImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LandDTO {
    private String name;
    private String province;
    private String district;
    private String ward;
    private int userId;
    private String location;
    private String description;
    private long price;
    private String contact;
    private MultipartFile document;
    private List<MultipartFile> images;
    private double square;
    private double width;
    private double length;
    private LocalDateTime createdAt;
}