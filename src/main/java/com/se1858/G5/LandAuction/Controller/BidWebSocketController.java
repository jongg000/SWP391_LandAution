package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.BidDTO;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Service.AuctionRegistrationService;
import com.se1858.G5.LandAuction.Service.BidService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class BidWebSocketController {

    private final BidService bidService;
    private final UserService userService;


    public BidWebSocketController(BidService bidService, UserService userService) {
        this.bidService = bidService;

        this.userService = userService;
    }

    @MessageMapping("/bids")
    @SendTo("/topic/bids")
    public BidDTO handleBid(BidDTO bidDTO) {
        BidDTO bid = new BidDTO();
        bid.setBidId(bidDTO.getBidId());
        bid.setBidAmount(bidDTO.getBidAmount());
        bid.setRegistrationId(bidDTO.getRegistrationId());
        bid.setBidTime(LocalDateTime.now());
        bidDTO.setLastName(userService.findByUserId(bidDTO.getUserId()).getLastName());
        bidService.saveBid(bid);
        return bidDTO;
    }


}
