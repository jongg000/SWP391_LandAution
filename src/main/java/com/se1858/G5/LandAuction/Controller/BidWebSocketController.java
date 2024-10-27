package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.BidDTO;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Service.AuctionRegistrationService;
import com.se1858.G5.LandAuction.Service.BidService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class BidWebSocketController {

    private final BidService bidService;


    public BidWebSocketController(BidService bidService, AuctionRegistrationService auctionRegisterService) {
        this.bidService = bidService;

    }

    @MessageMapping("/bids")
    @SendTo("/topic/bids")
    public BidDTO handleBid(BidDTO bidDTO) {
        bidService.saveBid(bidDTO);

        return bidDTO;
    }


}
