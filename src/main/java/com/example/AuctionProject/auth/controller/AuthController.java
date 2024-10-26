package com.example.AuctionProject.auth.controller;

import com.example.AuctionProject.auth.entity.LoginDto;
import com.example.AuctionProject.auth.security.JwtUtil;
import com.example.AuctionProject.auth.service.AuthService;
import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;


    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDao) {
        try {
            String token = authService.login(loginDao); // 로그인 서비스에서 토큰 생성 및 반환
            return ResponseEntity.ok(token); // 클라이언트에게 토큰을 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("로그인 중 오류가 발생했습니다. 시 시도해주세요.");
        }
    }


}
