package com.se1858.G5.LandAuction.DTO;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CancelAssetDTO {
    int id;
    String reason;
    String name;
    LocalDateTime date;
}
