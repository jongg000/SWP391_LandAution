package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionBidUpdateServiceImpl implements AuctionBidUpdateService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidsRepository bidsRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AssetRegistrationRepository assetRegistrationRepository;


    @Autowired
    private AuctionRegistrationRepository auctionRegistrationRepository;
    @Autowired
    private ViolationRepository violationRepository;

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
            Status status = null;
            Status status1 = null;
            if (auction.getStatus().getStatusID() == 13 || auction.getStatus().getStatusID() == 9 || auction.getStatus().getStatusID() == 17) {
                continue;
            } else if (auction.getStatus().getStatusID() == 11 && currentTime.isAfter(auction.getDepositTime())) {
                status = statusRepository.findById(9).orElse(null);
                Bids bids = bidsRepository.findByAuctionRegistration_AuctionAndBidAmount(auction, auction.getHighestBid());
                AuctionRegistration auctionRegistration = bids != null ? bids.getAuctionRegistration() : null;
                User bidder = (auctionRegistration != null && (auction.getStatus().getStatusID() == 11 || auction.getStatus().getStatusID() == 13)) ? auctionRegistration.getUser() : null;
                Violation violation = new Violation();
                violation.setUser(bidder);
                violation.setDetail("QUÁ HẠN THANH TOÁN TIỀN CỌC - CẤP ĐỘ 3");
                violationRepository.save(violation);
                status1 = statusRepository.findById(16).orElse(null);
                emailService.sendSimpleMail(bidder.getEmail(), "QUÁ HẠN THANH TOÁN CỌC", "Bạn đã không thanh toán cọc, lưu vào violation");
                emailService.sendSimpleMail(bidder.getEmail(), "QUÁ HẠN THANH TOÁN CỌC", "Bạn đã không thanh toán cọc, lưu vào violation");

            } else if (currentTime.isAfter(auction.getEndTime())) {
                List<Bids> bids = bidsRepository.findAllByAuctionRegistration_Auction(auction);
                if (bids.isEmpty()) {
                    status = statusRepository.findById(17).orElse(null);
                    status1 = statusRepository.findById(17).orElse(null);
                } else {
                    status = statusRepository.findById(11).orElse(null);
                    status1 = statusRepository.findById(11).orElse(null);
                }
            } else if (currentTime.isBefore(auction.getStartTime())) {
                status = statusRepository.findById(10).orElse(null);
                status1 = statusRepository.findById(10).orElse(null);

            } else {
                List<AuctionRegistration> ar = auctionRegistrationRepository.findAllByAuction_AuctionId(auction.getAuctionId());
                if (ar.isEmpty()) {
                    status = statusRepository.findById(17).orElse(null);
                    auction.setEndTime(auction.getStartTime().plusMinutes(2));
                    status1 = statusRepository.findById(17).orElse(null);
                } else {
                    status = statusRepository.findById(12).orElse(null);
                    status1 = statusRepository.findById(12).orElse(null);
                }
            }
            if (status != null && !status.equals(auction.getStatus())) {
                auction.setStatus(status);

                auctionRepository.save(auction);
            }
            AssetRegistration assetRegistration = assetRegistrationRepository.findAssetRegistrationByLand_LandId(auction.getLand().getLandId());
            if (assetRegistration.getStatus().getStatusID() != 4 && assetRegistration.getStatus().getStatusID() != 15 && status1 != null && !status1.equals(assetRegistration.getStatus())) {
                assetRegistration.setStatus(status1);
                assetRegistrationRepository.save(assetRegistration);
            }

        }
    }

}
