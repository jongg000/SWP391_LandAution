package com.se1858.G5.LandAuction.DTO;


import com.se1858.G5.LandAuction.Entity.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreateDTO {
    String email;
    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters")
    String password;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date dob;
    String gender;
    String nationalID;
    MultipartFile avatar;
    MultipartFile nationalFrontImage;
    MultipartFile nationalBackImage;
    Roles role;

}
