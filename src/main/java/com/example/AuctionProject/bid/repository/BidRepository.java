package com.example.AuctionProject.bid.repository;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.bid.entity.Bid;
import com.example.AuctionProject.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Optional<Bid> findTopByAuctionOrderByBidTimeDesc(Auction auction);
    List<Bid> findAllByAuction(Auction auction);

    //bid 테이블에서 모든 게시글의 현재 입찰자가 누군지 반환(기본키가 젤 큰)
    @Query("SELECT b FROM Bid b WHERE b.id IN (SELECT MAX(b2.id) FROM Bid b2 GROUP BY b2.auction.id)")
    List<Bid> findUniqueAuctionBids();

    List<Bid> findAllByUser(User user);

    Optional<User> findByUser(User user);
}
