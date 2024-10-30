package com.se1858.G5.LandAuction.Service;


import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.AuctionRegistrationRepository;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private LandRepository landRepository;
    @Autowired
    private AuctionRegistrationRepository auctionRegistration;
    @Autowired
    private UserRepository userRepository;

    public List<AuctionDto> getAllAuctions() {
        return auctionRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }


    public AuctionDto findAuctionById(int auctionId) {
        return auctionRepository.findById(auctionId)
                .map(this::convertToDTO)
                .orElse(null);
    }
    public List<AuctionDto> getTop4NewestAuctions(){
        return auctionRepository.findTop4ByOrderByStartTimeDesc().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }

    public void deleteAuction(int auctionId) {
        auctionRepository.deleteById(auctionId);
    }

    public AuctionDto update(AuctionDto auctionDto) {
        Auction auction = convertToEntity(auctionDto);
        Auction auctionUpdate = auctionRepository.save(auction);
        return convertToDTO(auctionUpdate);
    }

    AuctionDto convertToDTO(Auction auction) {
        return AuctionDto.builder().auctionId(auction.getAuctionId()).
                landId(auction.getLand().getLandId()).
                startTime(auction.getStartTime()).
                endTime(auction.getEndTime()).
                highestBid(auction.getHighestBid()).
                build();
    }

    private AuctionRegistrationDTO convertToAuctionRegistrationDTO(AuctionRegistration auction) {
        return AuctionRegistrationDTO.builder()
                .registrationId(auction.getRegistrationID())
                .auctionId(auction.getAuction().getAuctionId())
                .userId(auction.getUser().getUserId())
                .build();
    }

    private Auction convertToEntity(AuctionDto auctionDto) {
        Land land = landRepository.findById(auctionDto.getLandId()).orElse(null);
        return Auction.builder()
                .auctionId(auctionDto.getAuctionId())
                .land(land)
                .startTime(auctionDto.getStartTime())
                .endTime(auctionDto.getEndTime())
                .highestBid(auctionDto.getHighestBid())
                .build();
    }

    private AuctionRegistration convertToAuctionRegistrationEntity(AuctionRegistrationDTO auctionDto) {
        return AuctionRegistration.builder()
                .registrationID(auctionDto.getRegistrationId())
                .auction(auctionRepository.findById(auctionDto.getAuctionId()).orElse(null))
                .user(userRepository.findById(auctionDto.getUserId()).orElse(null))
                .build();
    }

    public boolean checkExistUserInAuction(int userId, int auctionId) {
        return auctionRegistration.existsByUser_UserIdAndAuction_AuctionId(userId, auctionId);
    }

}
