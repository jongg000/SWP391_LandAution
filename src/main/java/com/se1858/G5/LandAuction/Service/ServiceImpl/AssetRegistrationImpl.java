package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.AssetRegistrationRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetRegistrationImpl implements AssetRegistrationService {
    private final AssetRegistrationRepository assetRegistrationRepository;
    private final LandRepository landRepository;

    public AssetRegistrationImpl(AssetRegistrationRepository assetRegistrationRepository, LandRepository landRepository) {
        this.assetRegistrationRepository = assetRegistrationRepository;
        this.landRepository = landRepository;
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
    public List<AssetRegistration> findByUser(User user) {
        return assetRegistrationRepository.findByUser(user);
    }

    @Override
    public long countAssetRegistrationsByUser(User user) {
        return assetRegistrationRepository.count();
    }

    @Override
    public List<Integer> findByLandNameAndStatus(String landName, Status status) {
        return assetRegistrationRepository.findDocumentIdsByLandNameAndStatusId(landName, status);
    }
    @Override
    public long countAssetRegistrationsByStatus(Status status) {
        return assetRegistrationRepository.countByStatus(status);
    }



}
