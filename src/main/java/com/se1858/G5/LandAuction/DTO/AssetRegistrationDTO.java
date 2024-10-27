package com.se1858.G5.LandAuction.DTO;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public AssetRegistrationDTO() {
    }

    public AssetRegistrationDTO(String province, String district, String ward, String location, String description, Double price, String contact, int userid, MultipartFile document, List<MultipartFile> images) {
        Province = province;
        District = district;
        Ward = ward;
        Location = location;
        Description = description;
        this.price = price;
        this.contact = contact;
        this.userid = userid;
        this.document = document;
        this.images = images;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
