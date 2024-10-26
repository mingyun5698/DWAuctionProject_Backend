package com.example.AuctionProject.bid.dto;

import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidDto {
    private Long id;
    private AuctionDto auctionDto;
    private UserDto userDto;
    private LocalDateTime bidTime;
    private int bidFee;

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuctionDto getAuctionDto() {
        return auctionDto;
    }

    public void setAuctionDto(AuctionDto auctionDto) {
        this.auctionDto = auctionDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public int getBidFee() {
        return bidFee;
    }

    public void setBidFee(int bidFee) {
        this.bidFee = bidFee;
    }
}