package com.example.AuctionProject.auction.controller;

import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auction.service.AuctionService;
import com.example.AuctionProject.auction.service.ImageService;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class AuctionController {

    @Autowired
    AuctionRepository auctionRepository;
    
    @Autowired
    AuctionService auctionService;
    @Autowired
    ImageService imageService;;



    // 전체 Auction 리턴
    @GetMapping("/auction")
    public List<AuctionDto> getAuctions(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "type", defaultValue = "title") String type) {

        List<AuctionDto> auctionDtoList;
        System.out.println("search : " + search);


        if (search != null && !search.isEmpty()) {
            if ("username".equals(type)) {
                auctionDtoList = auctionService.toDtoList(auctionService.searchAuctionsByUserId(search));
            } else if ("title".equals(type)) {
                auctionDtoList = auctionService.toDtoList(auctionService.searchAuctionsByTitle(search));
            } else if ("productCategory".equals(type)) {
                auctionDtoList = auctionService.toDtoList(auctionService.searchAuctionByProductCategory(search));
            } else auctionDtoList = auctionService.toDtoList(auctionService.searchAuctionByProductName(search));
        } else {
            auctionDtoList = auctionService.toDtoList(auctionService.getAllAuctions());
        }
        return auctionDtoList;
    }


    // 동적 라우팅으로 인한 단일 게시판 리턴
    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionDto> idAuction(@PathVariable("id") Long id) {

        Auction auction = auctionRepository.findById(id).orElse(null);
        AuctionDto auctionDto = auctionService.toDto(auction);

        return ResponseEntity.ok(auctionDto);

    }

    // 경매글 생성
    @PostMapping("/auction/create")
    public ResponseEntity<?> createAuction(@ModelAttribute AuctionDto auctionDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam(value = "hoursToAdd", required = false) Integer hoursToAdd) {
        try {
            if (auctionDto.getTitle() != null && auctionDto.getContents() != null) {
                String imagePath = null;

                if (file != null && !file.isEmpty()) {
                    try {
                        imagePath = imageService.saveImage(file);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Failed to save image: " + e.getMessage());
                    }
                }

                if (imagePath == null) {
                    imagePath = "default_image.png";
                }


                AuctionDto savedAuction = auctionService.createAuction(auctionDto, userDetails, imagePath, hoursToAdd);
                return ResponseEntity.ok(savedAuction);
            } else {
                return ResponseEntity.badRequest().body("Invalid request parameters");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process request: " + e.getMessage());
        }
    }


    @DeleteMapping("/auction/{id}")
    public ResponseEntity<?> deleteAuction(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if((!userDetails.getUser().getId().equals(auctionRepository.findById(id).get().getUser().getId())) && !userDetails.getUser().getUserType().equals(UserRoleEnum.ADMIN)) {
            return ResponseEntity.badRequest().body("삭제 권한이 없습니다.");
        }

        if (auctionRepository.findById(id).get().getBidList().size() > 0 ) {
            return ResponseEntity.badRequest().body("입찰자가 있기 때문에 삭제가 불가능합니다.");
        }



        auctionRepository.deleteById(id);
        return ResponseEntity.ok("삭제완료");


    }

    @GetMapping("/auction/my")
    public ResponseEntity<?> myAuction(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Auction> auctions = auctionRepository.findAllByUser(userDetails.getUser());

        List<AuctionDto> auctionDtoList = auctionService.toDtoList(auctions);

        return ResponseEntity.ok(auctionDtoList);
    }


}
