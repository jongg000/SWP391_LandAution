package com.se1858.G5.LandAuction.Service;


import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import com.se1858.G5.LandAuction.Repository.LandImageRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LandService {
    @Autowired
    private  LandRepository landRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LandImageRepository landImageRepository;

    private LandDTO convertToDTO(Land land) {
        return LandDTO.builder()
                .landId(land.getLandId())
                .name(land.getName())
                .ward(land.getWard())
                .district(land.getDistrict())
                .province(land.getProvince())
                .userId(land.getUser().getUserId())
                .description(land.getDescription())
                .price(land.getPrice())
                .location(land.getLocation())
                .contact(land.getContact())
                .length(land.getLength())
                .width(land.getWidth())
                .square(land.getSquare())
                .build();
    }


    private Land convertToEntity(LandDTO land) {
        return Land.builder()
                .landId(land.getLandId())
                .name(land.getName())
                .width(land.getWidth())
                .length(land.getLength())
                .square(land.getSquare())
                .ward(land.getWard())
                .province(land.getProvince())
                .district(land.getDistrict())
                .contact(land.getContact())
                .description(land.getDescription())
                .price(land.getPrice())
                .location(land.getLocation())
                .user(userRepository.findById(land.getUserId()).orElse(null))
                .build();
    }
    private LandImage convertToLEntity(LandImageDTO land) {
        return LandImage.builder()
                .imageId(land.getImageId())
                .land(landRepository.findById(land.getLandId()).orElse(null))
                .imageUrl(land.getImageUrl())
                .build();
    }

    private LandImageDTO convertToLDTO(LandImage land) {
        return LandImageDTO.builder()
                .imageId(land.getImageId())
                .landId(land.getLand().getLandId())
                .imageUrl(land.getImageUrl())
                .build();
    }




    public void saveLand(LandDTO wishlistRequest) {
        Land wishlist = convertToEntity(wishlistRequest);
        landRepository.save(wishlist);
    }




    public List<LandDTO> findAllLand() {
        return landRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }




    public LandDTO findLandById(int wishlistId) {
        return landRepository.findById(wishlistId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<LandImageDTO> findAllLandImageByLandId(int landId){
        return landImageRepository.findByLand_LandId(landId)
                .stream().map(this::convertToLDTO)
                .collect(Collectors.toList());
    }

    public void deleteLandById(String wishlistId) {
        landRepository.deleteById(Integer.valueOf(wishlistId));
    }

    public LandDTO update(LandDTO landDTO) {
        Land land = convertToEntity(landDTO);
        Land udLand = landRepository.save(land);
        return convertToDTO(udLand);
    }

}

