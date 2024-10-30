package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;

import java.util.List;

public interface AssetService {
    List<AssetRegistration> getAllAssets();
    AssetRegistration getAssetByID(int id);
    AssetRegistration addAsset(AssetRegistration asset);

}
