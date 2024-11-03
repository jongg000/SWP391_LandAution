package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Status;

import java.util.List;

public interface AssetRegistrationService {
    public void save(AssetRegistration assetRegistration);
    public List<AssetRegistration> getAssetRegistrations();
    public AssetRegistration getAssetRegistrationByID(int id);
    public void updateAssetRegistration(AssetRegistration assetRegistration);
    public List<AssetRegistration> findByStatus(Status status);
    public List<AssetRegistration> findAll();


}
