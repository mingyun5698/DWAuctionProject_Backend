package com.example.AuctionProject.user.repository;

import com.example.AuctionProject.user.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByEmail(String userEmail);

    Optional<User> findByUserIdAndEmail(String UserId, String email);

    Optional<User> findByUserId(String userId);


}
