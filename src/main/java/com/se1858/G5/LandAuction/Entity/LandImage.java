package com.se1858.G5.LandAuction.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Land_Image")
public class LandImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    private String name;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Land land;

    @Column( nullable = false)
    private String imageUrl;
}

