package com.se1858.G5.LandAuction.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LandDTO {
    private Integer auctionId;
    private Integer statusId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer landId;
    private String description;
    private Double price;
    private String location;
    private String imageUrls;  // Danh sách URL của hình ảnh
    private String imageNames;  // Danh sách tên hình ảnh

    private String name;

    // Constructors
    public LandDTO(Integer auctionId, Integer statusId, LocalDateTime startTime, LocalDateTime endTime,
                   Integer landId, String description, Double price, String location,
                   String imageUrls, String imageNames, String name) {
        this.auctionId = auctionId;
        this.statusId = statusId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.landId = landId;
        this.description = description;
        this.price = price;
        this.location = location;
        this.imageUrls = imageUrls;
        this.imageNames = imageNames;
        this.name = name;
    }

    // Getters and Setters
    // ...
}