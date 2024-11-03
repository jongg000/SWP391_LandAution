package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Bids;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.BidsRepository;
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
}
