package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;

import java.util.List;

public interface AssetRegistrationService {
    public void save(AssetRegistration assetRegistration);
    public List<AssetRegistration> getAssetRegistrations();
    public AssetRegistration getAssetRegistrationByID(int id);
    public void updateAssetRegistration(AssetRegistration assetRegistration);
    public List<AssetRegistration> findByStatus(Status status);
    public List<AssetRegistration> findByUser(User user);
    public long countAssetRegistrationsByStatus(Status status);
    public long countAssetRegistrationsByUser(User user);
    public List<Integer> findByLandNameAndStatus(String landName, Status status);
}
