package com.example.AuctionProject.auction.dto;

import com.example.AuctionProject.bid.dto.BidDto;
import com.example.AuctionProject.successfulbid.dto.SuccessfulBidDto;
import com.example.AuctionProject.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuctionDto {
    private Long id;
    private UserDto userDto;
    private List<BidDto> bidDtos;
    private SuccessfulBidDto successfulBid;
    private String title;
    private String contents;
    private int bidFee;
    private LocalDateTime createTime;
    private LocalDateTime deadline;
    private String imagePath;
    private String productName;
    private String productCategory;

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<BidDto> getBidDtos() {
        return bidDtos;
    }

    public void setBidDtos(List<BidDto> bidDtos) {
        this.bidDtos = bidDtos;
    }

    public SuccessfulBidDto getSuccessfulBid() {
        return successfulBid;
    }

    public void setSuccessfulBid(SuccessfulBidDto successfulBid) {
        this.successfulBid = successfulBid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getBidFee() {
        return bidFee;
    }

    public void setBidFee(int bidFee) {
        this.bidFee = bidFee;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}