package com.example.AuctionProject.board.repository;

import com.example.AuctionProject.board.entity.Board;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

