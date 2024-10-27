package com.se1858.G5.LandAuction.DTO;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDTO {
    String email;
    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters")
    String password;
    String firstName;
    String lastName;
    String phoneNumber;

}
