package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Repository.AssetRegistrationRepository;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AssetRegistration> getAssetRegistrations() {
        return assetRegistrationRepository.findAll();
    }

    @Override
    public AssetRegistration getAssetRegistrationByID(int id) {
        return assetRegistrationRepository.getById(id);
    }


    @Override
    public void updateAssetRegistration(AssetRegistration assetRegistration) {
        assetRegistrationRepository.save(assetRegistration);
    }

    @Override
    public List<AssetRegistration> findByStatus(Status status) {
        return assetRegistrationRepository.findByStatus(status);
    }


    @Override
    public List<AssetRegistration> findAll() {
        return assetRegistrationRepository.findAll();
    }




}
