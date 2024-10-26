package com.example.AuctionProject.successfulbid.dto;

import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.user.dto.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessfulBidDto {
    private Long id;
    private AuctionDto auctionDto;
    private UserDto userDto;
    private Long auctionId;
    private Long bidUserId;
}
