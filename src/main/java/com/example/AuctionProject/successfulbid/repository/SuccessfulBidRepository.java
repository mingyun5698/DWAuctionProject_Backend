package com.example.AuctionProject.successfulbid.repository;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.successfulbid.entity.SuccessfulBid;
import com.example.AuctionProject.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SuccessfulBidRepository extends CrudRepository<SuccessfulBid, Long> {
    Optional<SuccessfulBid> findByAuction(Auction auction);

    List<SuccessfulBid> findAllByUser(User user);


}
