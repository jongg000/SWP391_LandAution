package com.se1858.G5.LandAuction.DTO;

import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int userId;
    private String userName;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String avatar;
    private Status status;private Float wallet; private Integer nationalID;
    private Date dob;
    private int roleId;
}
