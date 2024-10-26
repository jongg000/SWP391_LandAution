package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Repository.AssetRegistrationRepository;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class AssetRegistrationImpl implements AssetRegistrationService {
    private AssetRegistrationRepository assetRegistrationRepository;

    public AssetRegistrationImpl(AssetRegistrationRepository assetRegistrationRepository) {
        this.assetRegistrationRepository = assetRegistrationRepository;
    }

    @Override
    public void save(AssetRegistration assetRegistration) {
      assetRegistrationRepository.save(assetRegistration);
    }
}
