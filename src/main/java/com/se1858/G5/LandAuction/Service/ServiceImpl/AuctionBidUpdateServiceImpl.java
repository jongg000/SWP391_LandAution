package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Bids;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.BidsRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Service.AuctionBidUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionBidUpdateServiceImpl implements AuctionBidUpdateService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidsRepository bidsRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    public void updateHighestBidForEndedAuctions() {
        LocalDateTime currentTime = LocalDateTime.now();


        List<Auction> endedAuctions = auctionRepository.findAllByEndTimeBefore(currentTime);

        for (Auction auction : endedAuctions) {
            Optional<Bids> highestBid = bidsRepository.findTopByAuctionRegistration_AuctionOrderByBidAmountDesc(auction);
            highestBid.ifPresent(bid -> {
                auction.setHighestBid(bid.getBidAmount());
                auctionRepository.save(auction);
            });
        }
    }
    @Transactional
    public void updateStatus() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Auction> auctions = auctionRepository.findAll();

        for (Auction auction : auctions) {
            if(auction.getStatus().getStatusID()==13) {
                return;
            } else if(currentTime.isAfter(auction.getEndTime())) {
                auction.setStatus(statusRepository.findById(11).orElse(null));
            } else if (currentTime.isBefore(auction.getStartTime())) {
                auction.setStatus(statusRepository.findById(10).orElse(null));
            } else auction.setStatus(statusRepository.findById(12).orElse(null));
        }
    }

}
