package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.LandService;
import org.springframework.stereotype.Service;

@Service
public class LandServiceImpl implements LandService {
    private LandRepository landRepository;
    public LandServiceImpl(LandRepository landRepository) {
        this.landRepository = landRepository;
    }
    @Override
    public void save(Land land) {
        landRepository.save(land);
    }
}
