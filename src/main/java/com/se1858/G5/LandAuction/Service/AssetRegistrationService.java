package com.se1858.G5.LandAuction.Service;

//import com.se1858.G5.LandAuction.DTO.AssetSearchDTO;
import com.se1858.G5.LandAuction.DTO.FullAssetInfoDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.DTO.AssetInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetRegistrationService {
    public void save(AssetRegistration assetRegistration);
    List<AssetInfoDTO> findAssetInfoByDocumentId(Integer documentId);
    FullAssetInfoDTO findFullAssetInfoByDocumentId(Integer documentId);

    Page<FullAssetInfoDTO> findFullAssetInfo(Pageable page);//, AssetSearchDTO search
}
