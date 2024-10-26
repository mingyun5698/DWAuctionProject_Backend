package com.example.AuctionProject.user.entity;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.bid.entity.Bid;
import com.example.AuctionProject.board.entity.Board;
import com.example.AuctionProject.comment.entitiy.Comment;
import com.example.AuctionProject.successfulbid.entity.SuccessfulBid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId; // 사이트에서 사용하는 ID

    @JsonIgnore
    private String password;

    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum UserType;

    @Column(nullable = false)
    private String username; // 회원의 본명

    private String birthdate;

    private String gender;

    private String contact;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // 수정된 부분
    private List<Auction> auctions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SuccessfulBid> successfulBids;







}
