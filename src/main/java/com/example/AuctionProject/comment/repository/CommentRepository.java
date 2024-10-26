package com.example.AuctionProject.comment.repository;

import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.board.entity.Board;
import com.example.AuctionProject.comment.entitiy.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);
}
