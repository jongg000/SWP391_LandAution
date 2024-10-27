package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "Land_Image")
public class LandImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @ManyToOne
    @JoinColumn()
    private Land land;

    @Column( nullable = false)
    private String imageUrl;

}

