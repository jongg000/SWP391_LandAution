package com.se1858.G5.LandAuction.DTO;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LandDTO {
    private int landId;
    private Float price;
    private Set<String> images;
    private String description;
    private String location;
    private AuctionDto auctions;

    // Constructor, getters, and setters
    public LandDTO(int landId, Float price, Set<String> images, String description, String location, AuctionDto auctions) {
        this.landId = landId;
        this.price = price;
        this.images = images;
        this.description = description;
        this.location = location; // cập nhật location
        this.auctions = auctions;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AuctionDto getAuctions() {
        return auctions;
    }

    public void setAuctions(AuctionDto auctions) {
        this.auctions = auctions;
    }
}
