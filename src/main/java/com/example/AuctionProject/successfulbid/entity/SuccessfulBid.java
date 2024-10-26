package com.example.AuctionProject.successfulbid.entity;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "successfulBid")
public class SuccessfulBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "users_jd")
    private User user;


}
