package com.se1858.G5.LandAuction.Entity;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
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
