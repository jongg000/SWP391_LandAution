package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.AssetInfoDTO;
//import com.se1858.G5.LandAuction.DTO.AssetSearchDTO;
import com.se1858.G5.LandAuction.DTO.FullAssetInfoDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Repository.AssetRegistrationRepository;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<AssetInfoDTO> findAssetInfoByDocumentId(Integer documentId) {
        List<Object[]> results = assetRegistrationRepository.findAssetInfoByDocumentId(documentId);
        Map<Integer, AssetInfoDTO> assetInfoMap = new HashMap<>();

        for (Object[] row : results) {
            Integer docId = (Integer) row[0];

            AssetInfoDTO assetInfo = assetInfoMap.computeIfAbsent(docId, k -> {
                AssetInfoDTO dto = new AssetInfoDTO();
                dto.setDocumentId(docId);
//                dto.setApprovalDate((LocalDateTime) row[1]);
                Timestamp approvalTimestamp = (Timestamp) row[1];
                dto.setApprovalDate(approvalTimestamp != null ? approvalTimestamp.toLocalDateTime() : null);
                dto.setComments((String) row[2]);
                dto.setStatusId((Integer) row[3]);
                dto.setUserId((Integer) row[4]);
                dto.setLandId((Integer) row[5]);
                dto.setContact((String) row[6]);
                dto.setDescription((String) row[7]);
                dto.setDistrict((String) row[8]);
                dto.setLocation((String) row[9]);
                dto.setName((String) row[10]);
                dto.setPath((String) row[11]);
                dto.setPrice((Double) row[12]);
                dto.setProvince((String) row[13]);
                dto.setWard((String) row[14]);
                dto.setImages(new ArrayList<>());
                return dto;
            });

            // Thêm hình ảnh vào danh sách nếu có
            if (row[15] != null) {
                LandImageDTO image = new LandImageDTO();
                image.setImageId((Integer) row[15]);
                image.setImageUrl((String) row[16]);
//                image.setName((String) row[17]);
                assetInfo.getImages().add(image);
            }
        }

        return new ArrayList<>(assetInfoMap.values());
    }

    @Override
    public FullAssetInfoDTO findFullAssetInfoByDocumentId(Integer documentId) {
        return assetRegistrationRepository.findFullAssetInfoById(documentId);
    }

    @Override
    public Page<FullAssetInfoDTO> findFullAssetInfo(Pageable page) {//, AssetSearchDTO search
        return assetRegistrationRepository.findFullAssetInfo(page);
    }
}
