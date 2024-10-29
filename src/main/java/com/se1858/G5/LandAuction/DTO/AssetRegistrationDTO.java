package com.se1858.G5.LandAuction.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetRegistrationDTO {
    private String landId;
    private String statusId;
    private String reason;
    private String comments;
    private Date approvalDate;
    private LocalDateTime registrationDate;
    private Double price;
    private String contact;
    private int userid;
    private int documentId;
}
