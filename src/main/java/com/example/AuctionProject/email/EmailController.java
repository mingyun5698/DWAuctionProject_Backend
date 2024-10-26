package com.example.AuctionProject.email;

import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;


    Random random = new Random();
    int MAX_EMAIL_ASSOCIATED_ACCOUNTS = 3; // 이메일 하나당 만들수 있느 계정의 수

    //회원가입시 이메일 인증번호 발송
    @PostMapping("/email")
    public ResponseEntity<?> emailAuth(@RequestBody UserDto userDto) {
        int verificationCode = random.nextInt(888888) + 111111;


        List<User> users = userRepository.findAllByEmail(userDto.getEmail());
        if (users.size() > MAX_EMAIL_ASSOCIATED_ACCOUNTS-1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("하나의 이메일에" +MAX_EMAIL_ASSOCIATED_ACCOUNTS +"개의 계정만 만들 수 있습니다.");
        }
        else
            emailService.sendVerificationCode(userDto.getEmail(), verificationCode); // 인증번호 메일보내기
        return ResponseEntity.ok(verificationCode);

    }

    // 이메일로 아이디 찾기
    @PostMapping("/findid")
    public ResponseEntity<String> findMemberByEmail(@RequestBody UserDto userDto) {
        List<User> users = userService.findUsersByEmail(userDto.getEmail());

        if (!users.isEmpty()) {
            emailService.sendEmailWithMemberIds(userDto.getEmail(), users); // 아이디 메일 보내기
            return ResponseEntity.ok("이메일로 아이디를 보냈습니다.");
        } else {
            // 아이디가 존재하지 않을 경우 Bad Request 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디가 존재하지 않습니다.");
        }
    }

    //비밀번호 찾기
    @PostMapping("/findpassword")
    ResponseEntity<String> findUserByUserId(@RequestBody UserDto memberDto) {
        int verificationCode = random.nextInt(888888) + 111111;
        Optional<User> optionalUser = userService.findUserByUserId(memberDto);

        if(optionalUser.isPresent()) {
            userService.passwordResetByEmail(optionalUser.get(), verificationCode);
            emailService.sendEmailWithMemberPassword(memberDto.getEmail(), verificationCode);

            return ResponseEntity.ok("메일로 비밀번호가 보내졌습니다.");

        } return ResponseEntity.badRequest().body("아이디 또는 이메일이 존재하지 않습니다.");

    }
}
