package com.example.AuctionProject.successfulbid.controller;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.bid.entity.Bid;
import com.example.AuctionProject.bid.repository.BidRepository;
import com.example.AuctionProject.email.EmailService;
import com.example.AuctionProject.successfulbid.dto.SuccessfulBidDto;
import com.example.AuctionProject.successfulbid.entity.SuccessfulBid;
import com.example.AuctionProject.successfulbid.repository.SuccessfulBidRepository;
import com.example.AuctionProject.successfulbid.service.SuccessfulBidService;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class SuccessfulBidController {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private SuccessfulBidRepository successfulBidRepository;
    @Autowired
    private SuccessfulBidService successfulBidService;
    @Autowired
    private EmailService emailService;


    // 낙찰 DB 저장
    @PostMapping("/successfulbid")
    public ResponseEntity<?> createSuccessfulBid(@RequestBody SuccessfulBidDto successfulBidDto) {
        System.out.println("낙찰받은 DB 데이터 "+ successfulBidDto.getBidUserId());
        System.out.println("낙찰받은 DB 데이터 "+ successfulBidDto.getAuctionId());



        if(successfulBidDto.getBidUserId() == null) {
            return null;
        }



        Optional<User> userOptional= userRepository.findById(successfulBidDto.getBidUserId());
        Optional<Auction> auctionOptional = auctionRepository.findById(successfulBidDto.getAuctionId());

        System.out.println("낙찰받은 DB 데이터 "+ userOptional.get().getId());
        System.out.println("낙찰받은 DB 데이터 "+ auctionOptional.get().getId());

        if (successfulBidRepository.findByAuction(auctionOptional.get()).isPresent()) return null;




        if (userOptional.isPresent() && auctionOptional.isPresent()) {
            SuccessfulBid successfulBid = successfulBidService.createSuccessfulBidDto(successfulBidDto); // 낙찰 DB 생성 및 저장
            SuccessfulBidDto successfulBidDto1 = successfulBidService.toDto(successfulBid); // Dto 변환
            emailService.sendEmailWithSuccessfulbid(successfulBidDto1.getAuctionDto().getUserDto().getEmail(), successfulBidDto1.getAuctionDto(), successfulBidDto1.getUserDto());
            emailService.sendEmailWithSuccessfulbidUser(successfulBidDto1.getUserDto().getEmail(), successfulBidDto1.getAuctionDto());


            return ResponseEntity.ok(successfulBidDto1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid or Auction not found");
        }
    }


    //로그인한 유저의 낙찰 DB 리턴
    @GetMapping("/successfulbid")
    public ResponseEntity<List<SuccessfulBidDto>> successfulBid(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<SuccessfulBid> successfulBidList = successfulBidRepository.findAllByUser(userDetails.getUser());

        List<SuccessfulBidDto> successfulBidDtos = successfulBidService.toDtoList(successfulBidList);


        return ResponseEntity.ok(successfulBidDtos);
    }

    //모든 낙찰 DB 리턴
    @GetMapping("/allsuccessfulbid")
    public ResponseEntity<List<SuccessfulBidDto>> allSuccessfulBid() {
        List<SuccessfulBid> successfulBidList = (List<SuccessfulBid>) successfulBidRepository.findAll();

        List<SuccessfulBidDto> successfulBidDtos = successfulBidService.toDtoList(successfulBidList);


        return ResponseEntity.ok(successfulBidDtos);
    }


}
