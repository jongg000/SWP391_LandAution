package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {

     AuctionRepository auctionRepository;
     LandRepository landRepository;
     AuctionRegistrationRepository auctionRegistration;
     UserRepository userRepository;
     AuctionBidUpdateServiceImpl auctionBidUpdateService;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository, LandRepository landRepository, AuctionRegistrationRepository auctionRegistration, UserRepository userRepository, AuctionBidUpdateServiceImpl auctionBidUpdateService) {
        this.auctionRepository = auctionRepository;
        this.landRepository = landRepository;
        this.auctionRegistration = auctionRegistration;
        this.userRepository = userRepository;
        this.auctionBidUpdateService = auctionBidUpdateService;
    }

    public AuctionDto findAuctionById(int auctionId) {
        return auctionRepository.findById(auctionId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<AuctionDto> getAllAuctions() {
        return auctionRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
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

    public AuctionDto convertToDTO(Auction auction) {
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

    @Scheduled(fixedRate = 1000)
    public void scheduledUpdateHighestBid() {
        auctionBidUpdateService.updateHighestBidForEndedAuctions();
    }
    @Override
    public long getTotalAuctions() {
        return auctionRepository.count();
    }
    public List<Auction> findAllAuctionEnd() {
        return auctionRepository.findAllByEndTimeBefore(LocalDateTime.now());

    }

    @Override
    public void saveAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public List<Auction> getAllAuctionByStartTime() {
        return auctionRepository.findAllByOrderByStartTimeDesc();
    }

    @Override
    public long countByStatus(Status status) {
        return auctionRepository.countByStatus(status);
    }

    @Override
    public Auction findAuctionByLand(Land land) {
        return auctionRepository.findAuctionByLand(land);
    }


}