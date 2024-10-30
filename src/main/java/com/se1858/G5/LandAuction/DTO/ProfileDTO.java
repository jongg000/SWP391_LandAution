package com.se1858.G5.LandAuction.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;
    private MultipartFile avatar;
    private MultipartFile nationalFrontImage;
    private MultipartFile nationalBackImage;
    private String email;
    private String gender;
    private String nationalID;

}
