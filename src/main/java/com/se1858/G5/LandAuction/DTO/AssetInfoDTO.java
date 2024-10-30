package com.se1858.G5.LandAuction.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssetInfoDTO {
    private Integer documentId;
    private LocalDateTime approvalDate;
    private String comments;
    private Integer statusId;
    private Integer userId;

    // Thông tin đất (land)
    private Integer landId;
    private String contact;
    private String description;
    private String district;
    private String location;
    private String name;
    private String path;
    private Double price;
    private String province;
    private String ward;

    // Danh sách hình ảnh
    private List<LandImageDTO> images;
}
