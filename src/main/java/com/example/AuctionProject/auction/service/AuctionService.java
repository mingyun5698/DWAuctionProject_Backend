package com.example.AuctionProject.auction.service;


import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService {

private final AuctionRepository auctionRepository;
private final UserService userService;

    public List<Auction> getAllAuctions() {
        return (List<Auction>) auctionRepository.findAll();
    }

    public List<Auction> searchAuctionsByTitle(String title) {
        return auctionRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Auction> searchAuctionsByUserId(String userId) {
        return auctionRepository.findByUserUserIdContainingIgnoreCase(userId);
    }

    public List<Auction> searchAuctionByProductCategory(String productCategory) {
        return auctionRepository.findByProductCategoryContainingIgnoreCase(productCategory);
    }

    public List<Auction> searchAuctionByProductName(String productName) {
        return auctionRepository.findByProductNameContainingIgnoreCase(productName);
    }


public AuctionDto createAuction(AuctionDto auctionDto, UserDetailsImpl userDetails, String imagePath, int hoursToAdd) {
    Auction auction = new Auction();
    auction.setTitle(auctionDto.getTitle());
    auction.setContents(auctionDto.getContents());
    auction.setCreateTime(LocalDateTime.now());
    auction.setDeadline(LocalDateTime.now().plusSeconds(60));
    auction.setBidFee(auctionDto.getBidFee());
    auction.setImagePath(imagePath.toString());
    auction.setProductCategory(auctionDto.getProductCategory());
    auction.setProductName(auctionDto.getProductName());

    if(imagePath.toString().equals("default_image.png")) auction.setImagePath(null);



    User user = userDetails.getUser();


    auction.setUser(user);
    auction.setBidFee(auction.getBidFee());

    auctionRepository.save(auction);

    AuctionDto auctionDto1 = toDto(auction);



    return auctionDto1;
}

    public List<AuctionDto> toDtoList(Iterable<Auction> auctions) {
        List<AuctionDto> auctionDtos = new ArrayList<>();
        for (Auction auction : auctions) {
            AuctionDto auctionDto = toDto(auction);
            auctionDtos.add(auctionDto);
        }
        return auctionDtos;
    }

    public AuctionDto toDto(Auction auction) {
        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setId(auction.getId());
        auctionDto.setTitle(auction.getTitle());
        auctionDto.setContents(auction.getContents());
        auctionDto.setCreateTime(auction.getCreateTime());
        auctionDto.setDeadline(auction.getDeadline());
        auctionDto.setBidFee(auction.getBidFee());
        auctionDto.setImagePath(auction.getImagePath());
        auctionDto.setProductCategory(auction.getProductCategory());
        auctionDto.setProductName(auction.getProductName());

        UserDto userDto = userService.toDto(auction.getUser());
        auctionDto.setUserDto(userDto);

        return auctionDto;

    }
}
