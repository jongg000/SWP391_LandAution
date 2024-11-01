package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.AssetService;
import com.se1858.G5.LandAuction.Service.LandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetServiceImpl implements AssetService {

    @Autowired
    private LandRepository landRepository;


    @Override
    public List<AssetRegistration> getAllAssets() {
        return List.of();
    }

    @Override
    public AssetRegistration getAssetByID(int id) {
        return null;
    }

    @Override
    public AssetRegistration addAsset(AssetRegistration asset) {
        return null;
    }

    @Override
    public List<Land> findAll() {
        return landRepository.findAll();
    }

    @Override
    public List<Land> findAllByName(String keyword) {
        return landRepository.findAllByName(keyword);
    }

    @Override
    public List<Land> search(String keyword) {
        return landRepository.searchLandsByKeyword(keyword);
    }

    @Override
    public List<Land> findTop4ByOrderByLandIdDesc() {
        return landRepository.findTop4ByOrderByLandIdDesc();
    }
}
