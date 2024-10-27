package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.DTO.AssetInfoDTO;

import java.util.List;

public interface AssetRegistrationService {
    public void save(AssetRegistration assetRegistration);
    List<AssetInfoDTO> findAssetInfoByDocumentId(Integer documentId);
}
