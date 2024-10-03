package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidsService {
    private final BidsRepository bidsRepository;

    @Autowired
    public BidsService(BidsRepository bidsRepository) {
        this.bidsRepository = bidsRepository;
    }
}
