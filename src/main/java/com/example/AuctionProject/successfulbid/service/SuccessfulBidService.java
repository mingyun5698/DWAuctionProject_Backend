package com.example.AuctionProject.successfulbid.service;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auction.service.AuctionService;
import com.example.AuctionProject.bid.entity.Bid;
import com.example.AuctionProject.bid.repository.BidRepository;
import com.example.AuctionProject.successfulbid.dto.SuccessfulBidDto;
import com.example.AuctionProject.successfulbid.entity.SuccessfulBid;
import com.example.AuctionProject.successfulbid.repository.SuccessfulBidRepository;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SuccessfulBidService {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SuccessfulBidRepository successfulBidRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private UserRepository userRepository;


    public SuccessfulBid createSuccessfulBidDto(SuccessfulBidDto successfulBidDto) {
        Optional<User> userOptional = userRepository.findById(successfulBidDto.getBidUserId());
        Optional<Auction> auctionOptional = auctionRepository.findById(successfulBidDto.getAuctionId());

        SuccessfulBid successfulBid = new SuccessfulBid();
        successfulBid.setAuction(auctionOptional.get());
        successfulBid.setUser(userOptional.get());

        successfulBidRepository.save(successfulBid);

        return successfulBid;
    }

    public SuccessfulBidDto toDto(SuccessfulBid successfulBid) {


        SuccessfulBidDto successfulBidDto = new SuccessfulBidDto();

        successfulBidDto.setAuctionDto(auctionService.toDto(successfulBid.getAuction()));
        successfulBidDto.setId(successfulBid.getId());
        successfulBidDto.setUserDto(userService.toDto(successfulBid.getUser()));

        return successfulBidDto;
    }

    public List<SuccessfulBidDto> toDtoList(List<SuccessfulBid> successfulBidList) {
        List<SuccessfulBidDto> successfulBidDtos = new ArrayList<>();
        for (SuccessfulBid successfulBid : successfulBidList) {
            SuccessfulBidDto successfulBidDto = toDto(successfulBid);

            successfulBidDtos.add(successfulBidDto);
        } return successfulBidDtos;
    }
}
