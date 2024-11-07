package com.se1858.G5.LandAuction.DTO;


import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private int id;
    @Size(max = 20, message = "Nội dung chỉ được phép tối đa 20 ký tự.")
    private String title;
    @Size(max = 1000, message = "Nội dung chỉ được phép tối đa 1000 ký tự.")
    private String content;
    private User user;
    private MultipartFile image;
    private String time;
}
