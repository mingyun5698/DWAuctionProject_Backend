package com.example.AuctionProject.auth.service;

import com.example.AuctionProject.auth.entity.LoginDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.auth.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByUserId(loginDto.getUserId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getUserId(), user.getUserType());

        return token;
    }



}
