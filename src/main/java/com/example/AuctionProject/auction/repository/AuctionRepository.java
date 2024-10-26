package com.example.AuctionProject.auction.repository;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
    List<Auction> findByTitleContainingIgnoreCase(String title);
    List<Auction> findByUserUserIdContainingIgnoreCase(String userId);
    List<Auction> findByProductCategoryContainingIgnoreCase(String productCategory);
    List<Auction> findByProductNameContainingIgnoreCase(String productName);

    List<Auction> findAllByUser(User user);
}
