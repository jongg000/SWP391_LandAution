package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.Service.AuctionRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class AuctionRegistrationServiceImpl implements AuctionRegistrationService {

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
    @Autowired
    private LandRepository landRepository;

    @Override
    public AuctionRegistrationDTO convertToDTO(AuctionRegistration auction) {
        return AuctionRegistrationDTO.builder()
                .registrationId(auction.getRegistrationID())
                .auctionId(auction.getAuction().getAuctionId())
                .userId(auction.getUser().getUserId())
                .build();
    }
    @Override
    public AuctionRegistration convertToEntity(AuctionRegistrationDTO land) {
        return AuctionRegistration.builder()
                .registrationID(land.getRegistrationId())
                .auction(auctionRepository.findById(land.getAuctionId()).orElse(null))
                .user(userRepository.findById(land.getUserId()).orElse(null))
                .build();
    }
    @Override
    public AuctionRegistrationDTO getById(int id) {
        return convertToDTO(Objects.requireNonNull(auctionRegistrationRepository.findById(id).orElse(null)));

    }
    @Override
    public AuctionRegistration save(AuctionRegistrationDTO auctionDto) {
        AuctionRegistration auction = convertToEntity(auctionDto);
        return auctionRegistrationRepository.save(auction);
    }
    @Override
    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByAuctionId(int auctionId) {
        return auctionRegistrationRepository.findAllByAuction_AuctionId(auctionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Override
    public AuctionRegistrationDTO getByUser_UserIdAndAuction_AuctionId(int userId, int auctionId) {
        return convertToDTO(auctionRegistrationRepository.findByUser_UserIdAndAuction_AuctionId(userId, auctionId));
    }
    @Override
    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByUserId(int userId) {
        return auctionRegistrationRepository.findAllByUser_UserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Override
    public boolean checkAvailableAttend(int userId, int landId) {
        Land land = landRepository.findById(landId).orElse(null);

        return land.getUser().getUserId() == userId;
    }
    @Override
    public AuctionRegistration getByAuctionAndBidAmount(Auction auction, long bidAmount) {
        return auctionRegistrationRepository.findByAuctionAndBids_BidAmount( auction, bidAmount);
    }

    @Override
    public List<User> getUserInAuction(Auction auction) {
         List<AuctionRegistration> as = auctionRegistrationRepository.findAllByAuction_AuctionId(auction.getAuctionId());
         List<User> users = new ArrayList<>();
         for(AuctionRegistration a : as) {
             users.add(a.getUser());
         }
         return users;
    }

    @Override
    public boolean checkAvailableAttend(int userId){
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        return user.getStatus().getStatusID() == 2;
    }

}
