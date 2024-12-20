package com.se1858.G5.LandAuction.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "Land")
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int landId;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String province;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String district;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String ward;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn( nullable = false)
    private User user;

    @Column( columnDefinition = "TEXT")
    private String location;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;
    private long price;

    private String contact;

    @Column(nullable = true)
    private double square;

    @Column(nullable = true)
    private double width;

    @Column(nullable = true)
    private double length;

    private String path;


    @OneToOne(mappedBy = "land", cascade = CascadeType.ALL)
    private AssetRegistration assetRegistration;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL)
    private List<LandImage> images;

    public Land(double length, double width, double square, String contact, long price, String description, String location, User user, String name, String ward, String district, String province) {
        this.length = length;
        this.width = width;
        this.square = square;
        this.contact = contact;
        this.price = price;
        this.description = description;
        this.location = location;
        this.user = user;
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.square = square;
    }

    public void addImg(LandImage image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        // Thêm hình ảnh vào danh sách
        images.add(image);
        // Cập nhật mối quan hệ từ LandImage tới Land
        image.setLand(this); // Thiết lập mối quan hệ ngược lại
    }

}

