package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.LandDTO;

import java.util.List;

public interface LandService {
    List<LandDTO> findAllLandDetailsWithAuctions();
}
