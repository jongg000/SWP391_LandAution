package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuctionRegistrationService {
    @Autowired
    private AuctionRegistrationRepository auctionRegistrationRepository;
    @Autowired
    private AssetRegistrationRepository assetRegistrationRepository;

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;

    private AuctionRegistrationDTO convertToDTO(AuctionRegistration auction) {
        return AuctionRegistrationDTO.builder()
                .registrationId(auction.getRegistrationID())
                .auctionId(auction.getAuction().getAuctionId())
                .userId(auction.getUser().getUserId())
                .build();
    }

    private AuctionRegistration convertToEntity(AuctionRegistrationDTO land) {
        return AuctionRegistration.builder()
                .registrationID(land.getRegistrationId())
                .auction(auctionRepository.findById(land.getAuctionId()).orElse(null))
                .user(userRepository.findById(land.getUserId()).orElse(null))
                .build();
    }
    public AuctionRegistrationDTO getById(int id) {
        return convertToDTO(Objects.requireNonNull(auctionRegistrationRepository.findById(id).orElse(null)));

    }
    public AuctionRegistration save(AuctionRegistrationDTO auctionDto) {
        AuctionRegistration auction = convertToEntity(auctionDto);
        return auctionRegistrationRepository.save(auction);
    }

    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByAuctionId(int auctionId) {
        return auctionRegistrationRepository.findAllByAuction_AuctionId(auctionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuctionRegistrationDTO getByUser_UserIdAndAuction_AuctionId(int userId, int auctionId) {
        return convertToDTO(auctionRegistrationRepository.findByUser_UserIdAndAuction_AuctionId(userId, auctionId));
    }
    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByUserId(int userId) {
        return auctionRegistrationRepository.findAllByUser_UserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public boolean checkAvailableAttend(int userId, int landId) {
        AssetRegistration ar = assetRegistrationRepository.findAssetRegistrationByLand_LandId(landId);
        return ar.getUser().getUserId() == userId;
    }

    public boolean checkAvailableAttend(int userId){
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        return user.getStatus().getStatusID() == 2;
    }
}


