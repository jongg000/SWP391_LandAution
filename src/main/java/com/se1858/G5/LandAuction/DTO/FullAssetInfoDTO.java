package com.se1858.G5.LandAuction.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class FullAssetInfoDTO {
    private Integer documentId;          // từ Asset_Registration
    private LocalDateTime registrationDate; // từ Asset_Registration
    private Date approvalDate;  // từ Asset_Registration
    private String reason;                // từ Asset_Registration
    private String comments;              // từ Asset_Registration
    private Integer userId;               // từ Asset_Registration

    // Thông tin đất
    private Integer landId;               // từ Land
    private String province;               // từ Land
    private String district;               // từ Land
    private String ward;                   // từ Land
    private String name;                   // từ Land
    private String location;               // từ Land
    private String description;            // từ Land
    private Long price;                    // từ Land
    private String contact;                // từ Land
    private Double square;                 // từ Land
    private Double width;                  // từ Land
    private Double length;                 // từ Land
    private String path;                   // từ Land

    // Danh sách hình ảnh
    public List<LandImageDTO> images;    // từ Land_Image
    public String imagesUrl;    // từ Land_Image
    public String statusName;
    // Constructor
    public FullAssetInfoDTO(Integer documentId, LocalDateTime registrationDate, Date approvalDate,
                            String reason, String comments, Integer userId,
                            Integer landId, String province, String district, String ward,
                            String name, String location, String description,
                            Long price, String contact, Double square, Double width, Double length, String path, String statusName) {
        this.documentId = documentId;
        this.registrationDate = registrationDate;
        this.approvalDate = approvalDate;
        this.reason = reason;
        this.comments = comments;
        this.userId = userId;
        this.landId = landId;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.name = name;
        this.location = location;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.square = square;
        this.width = width;
        this.length = length;
        this.path = path;
        this.statusName = statusName;
    }
}
