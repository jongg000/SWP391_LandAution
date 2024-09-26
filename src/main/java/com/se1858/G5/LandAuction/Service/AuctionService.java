package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction findAuctionById(int auctionId) {
        return auctionRepository.findById(auctionId).orElse(null);
    }

    public void deleteAuction(int auctionid) {
        auctionRepository.deleteById(auctionid);
    }

    public Auction update(Auction auction) {
        return auctionRepository.save(auction);
    }
}
