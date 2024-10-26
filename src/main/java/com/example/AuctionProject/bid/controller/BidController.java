package com.example.AuctionProject.bid.controller;

import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.bid.dto.BidDto;
import com.example.AuctionProject.bid.entity.Bid;
import com.example.AuctionProject.bid.repository.BidRepository;
import com.example.AuctionProject.bid.service.BidService;
import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class BidController {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BidService bidService;
    @Autowired
    private UserRepository userRepository;



    @PostMapping("/bid")
    public ResponseEntity<?> doBid(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Auction auction) {

        // 경매 정보 가져오기
        Auction auction1 = auctionRepository.findById(auction.getId()).orElse(null);
        if (auction1 == null) {
            return ResponseEntity.notFound().build(); // 경매 정보가 없을 경우
        }

        List<Bid> bidList =  auction1.getBidList();
        if(bidList.size() > 0 && bidList.get(bidList.size() - 1).getUser().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.badRequest().body("이미 입찰하셨습니다."); // 수정: ResponseEntity를 반환합니다.
        }


        // 작성자와 입찰자 확인
        if (userDetails.getUser().getId().equals(auction1.getUser().getId())) {
            return ResponseEntity.badRequest().body("자신의 올린 물품은 입찰할 수 없습니다.");
        }

        // 입찰료 비교 검증
        if (auction1.getBidFee() >= auction.getBidFee()) {
            System.out.println("입찰 :" +  auction.getBidFee());
            System.out.println("현재 :" +  auction1.getBidFee());
            return ResponseEntity.badRequest().body("현재 이적료보다 더 큰 금액을 써야합니다.");
        }

        // 경매DB의 입찰료 업데이트
        auction1.setBidFee(auction.getBidFee());
        auctionRepository.save(auction1);

        // BID 데이터 생성 및 저장
        Bid bid = bidService.createbid(auction, userDetails.getUser());

        // 입찰한 사용자 DTO 생성 및 반환
        UserDto userDto = userService.toDto(bid.getUser());
        BidDto bidDto = new BidDto();
        bidDto.setUserDto(userDto);

        // 입찰한 사용자 DTO 리턴
        return ResponseEntity.ok(bidDto.getUserDto());
    }

    // 게시글의 모든 bid 기록을 리턴
    @GetMapping("/bidlist")
    public ResponseEntity<?> bidData(@RequestParam("id")Long id) {
        System.out.println(id);
        List<Bid> bidList = bidRepository.findAllByAuction(auctionRepository.findById(id).get());

        List<BidDto> bidDtoList = bidService.toDtoList(bidList);
        return ResponseEntity.ok(bidDtoList);
    }



    // 경매글 클릭시 경매에 대한 id값을 받고 입찰자의 userDto를 반환
    @GetMapping("biduser")
    public ResponseEntity<?> bidUser(@RequestParam("id") Long id) {
        System.out.println("받은 id: " + id);
        Auction auction = auctionRepository.findById(id).get();
        Optional<Bid> bid = bidRepository.findTopByAuctionOrderByBidTimeDesc(auction);

        if(!bid.isPresent()) return ResponseEntity.ok("");

        else {
            UserDto userDto = userService.toDto(bid.get().getUser());
            return ResponseEntity.ok(userDto);
        }


    }


    // 로그인 User 데이터를 받으면 경매글은 중복하지 않으면서 자신의 입찰기록이 있는 Bid데이터를 리턴
    @GetMapping("/bidunique")
    public ResponseEntity<List<BidDto>> bidunique(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Bid> bidList1 = bidRepository.findAllByUser(userDetails.getUser()); // 내 bid 기록 List
        List<BidDto> bidDtoList = bidService.toDtoList(bidList1); // bid를 bidDto로 변경
        List<BidDto> uniqueBidDtoList = bidService.getUniqueBids(bidDtoList); // 내 bid기록중에 게시글당 가장 최근 입찰기록을 반환

        return ResponseEntity.ok(uniqueBidDtoList);
    }


    // 현재 모든 게시글의 각각 현재 입찰자가 누군지 리턴
    @GetMapping("/bidnow")
    public ResponseEntity<?> bidnow() {
        List<Bid> bidList = bidRepository.findUniqueAuctionBids();
        List<BidDto> bidDtoList = bidService.toDtoList(bidList);
        return ResponseEntity.ok(bidDtoList);
    }



}
