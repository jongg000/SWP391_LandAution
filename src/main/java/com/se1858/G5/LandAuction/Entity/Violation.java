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
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vioId;

    @Column(columnDefinition = "Nvarchar(MAX)")
    private String detail;

    @ManyToOne
    @JoinColumn(name ="UserID")
    private User user;

}
