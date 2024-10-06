package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Land_Image")
public class LandImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID")
    private int imageId;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @Column(name = "Image_URL", nullable = false)
    private String imageUrl;
}

