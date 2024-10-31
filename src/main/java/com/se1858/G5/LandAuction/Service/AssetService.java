package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {
    List<AssetRegistration> getAllAssets();
    AssetRegistration getAssetByID(int id);
    AssetRegistration addAsset(AssetRegistration asset);
    List<Land> findAll();
    List<Land> findAllByName(String keyword);

}
