package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;


public interface LandService {
    public void save(Land land);
    Page<LandDTO> findAuctionDetailsWithImages(Pageable pageable);

}
