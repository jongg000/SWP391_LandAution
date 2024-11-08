package com.se1858.G5.LandAuction.Service;


import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.LandImageRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface LandService {


     LandDTO convertToDTO(Land land);


     Land convertToEntity(LandDTO land);
     LandImage convertToLEntity(LandImageDTO land);

     LandImageDTO convertToLDTO(LandImage land);


     void save(Land land);


     void saveLand(LandDTO wishlistRequest);

     List<LandDTO> findAllLand();


     LandDTO findLandById(int wishlistId);

     List<LandImageDTO> findAllLandImageByLandId(int landId);

     void deleteLandById(String wishlistId);
     LandDTO update(LandDTO landDTO);

     List<Land> findByUser(User user);
     Long countByUser(User user);
}

