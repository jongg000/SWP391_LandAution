package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.LandImageRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LandServiceImpl implements LandService {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LandImageRepository landImageRepository;

    @Override
    public LandDTO convertToDTO(Land land) {
        return LandDTO.builder()
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

    @Override
    public Land convertToEntity(LandDTO land) {
        return Land.builder()
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
    @Override
    public LandImage convertToLEntity(LandImageDTO land) {
        return LandImage.builder()
                .imageId(land.getImageId())
                .land(landRepository.findById(land.getLandId()).orElse(null))
                .imageUrl(land.getImageUrl())
                .build();
    }
    @Override
    public LandImageDTO convertToLDTO(LandImage land) {
        return LandImageDTO.builder()
                .imageId(land.getImageId())
                .landId(land.getLand().getLandId())
                .imageUrl(land.getImageUrl())
                .build();
    }

    @Override
    public void save(Land land){
        landRepository.save(land);
    }

    @Override
    public void saveLand(LandDTO wishlistRequest) {
        Land wishlist = convertToEntity(wishlistRequest);
        landRepository.save(wishlist);
    }
    @Override
    public List<LandDTO> findAllLand() {
        return landRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }

    @Override
    public LandDTO findLandById(int wishlistId) {
        return landRepository.findById(wishlistId)
                .map(this::convertToDTO)
                .orElse(null);
    }
    @Override
    public List<LandImageDTO> findAllLandImageByLandId(int landId){
        return landImageRepository.findByLand_LandId(landId)
                .stream().map(this::convertToLDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteLandById(String wishlistId) {
        landRepository.deleteById(Integer.valueOf(wishlistId));
    }
    @Override
    public LandDTO update(LandDTO landDTO) {
        Land land = convertToEntity(landDTO);
        Land udLand = landRepository.save(land);
        return convertToDTO(udLand);
    }
    @Override
    public List<Land> findByUser(User user) {
        return  landRepository.findByUser(user);
    }

}
