package com.se1858.G5.LandAuction.DTO;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import com.se1858.G5.LandAuction.Entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDTO {

    // Fields from AssetRegistration
    private int documentId;
    private String status;
    private String registrationDate;
    private String reason;
    private String userName; // Assuming User has a name
    private String comments;
    // Fields from Land
    private int landId;
    private String province;
    private String district;
    private String ward;
    private String name;
    private String location;
    private String description;
    private long price;
    private String contact;
    private double square;
    private double width;
    private double length;
    private String path;
    private List<String> imageUrls; // List of image paths or URLs\
    private String auctionStat;

    public RequestDTO convertToDTO(AssetRegistration assetRegistration) {
        if (assetRegistration == null) {
            return null;
        }

        Land land = assetRegistration.getLand();
        User user = assetRegistration.getLand().getUser();

        return RequestDTO.builder()
                // AssetRegistration fields
                .documentId(assetRegistration.getDocumentId())
                .status(assetRegistration.getStatus() != null ? assetRegistration.getStatus().getName() : null) // assuming status has a 'name' field
                .registrationDate(assetRegistration.getRegistrationDate().toString())
                .reason(assetRegistration.getReason())
                .userName(user != null ? user.getFirstName() + " " + user.getLastName() : null) // assuming User has first and last name
                .comments(assetRegistration.getComments())

                // Land fields
                .landId(land.getLandId())
                .province(land.getProvince())
                .district(land.getDistrict())
                .ward(land.getWard())
                .name(land.getName())
                .location(land.getLocation())
                .description(land.getDescription())
                .price(land.getPrice())
                .contact(land.getContact())
                .square(land.getSquare())
                .width(land.getWidth())
                .length(land.getLength())
                .path(land.getPath())

                // Land images
                .imageUrls(land.getImages() != null
                        ? land.getImages().stream().map(LandImage::getImageUrl).collect(Collectors.toList())
                        : new ArrayList<>()) // assuming LandImage has a 'path' field
                .build();
    }
}

