package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.LandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LandServiceImpl implements LandService {
    private LandRepository landRepository;
    public LandServiceImpl(LandRepository landRepository) {
        this.landRepository = landRepository;
    }
    @Override
    public void save(Land land) {
        landRepository.save(land);
    }

    @Override
    public Page<LandDTO> findAuctionDetailsWithImages(Pageable pageable) {
        Page<Tuple> rs = landRepository.findAuctionDetailsWithImages(pageable);
        List<LandDTO> landDTOs = new ArrayList<>();

        for (Tuple tuple : rs.getContent()) {
            landDTOs.add(convertToLandDTO(tuple));
        }
        return new PageImpl<>(landDTOs, pageable, rs.getTotalElements());
    }

    private LandDTO convertToLandDTO(Tuple tuple) {
        LandDTO landDTO = new LandDTO();

        landDTO.setLandName(tuple.get("name", String.class));
        landDTO.setLocation(tuple.get("location", String.class));
        landDTO.setDescription(tuple.get("description", String.class));
        // Chuyển đổi từ BigDecimal sang Long cho trường price
        BigDecimal price = tuple.get("price", BigDecimal.class);
        if (price != null) {
            landDTO.setPrice(price.longValue());  // Chuyển BigDecimal sang Long
        } else {
            landDTO.setPrice(0L);  // Giá trị mặc định nếu price là null
        }

        landDTO.setContact(tuple.get("contact", String.class));  // Thêm contact
        landDTO.setProvince(tuple.get("province", String.class));  // Thêm province
        landDTO.setDistrict(tuple.get("district", String.class));  // Thêm district
        landDTO.setWard(tuple.get("ward", String.class));  // Thêm ward
        Double square = tuple.get("square", Double.class);
        landDTO.setSquare(square != null ? square : 0.0);

        Double width = tuple.get("width", Double.class);
        landDTO.setWidth(width != null ? width : 0.0);

        Double length = tuple.get("length", Double.class);
        landDTO.setLength(length != null ? length : 0.0);  // Thêm length

        // Xử lý danh sách hình ảnh (images)
        String imageUrls = tuple.get("imageUrls", String.class);
//        landDTO.setImages(imageUrls);  // Phương thức phụ để chuyển chuỗi hình ảnh thành List

        return landDTO;
    }



}
