package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @Column(name = "ImageURL", nullable = false)
    private String imageUrl;
}

