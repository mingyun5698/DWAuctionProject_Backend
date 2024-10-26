package com.example.AuctionProject.user.controller;


import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import com.example.AuctionProject.auth.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;



    //회원가입 AP
    @PostMapping("/users/signup")
    public ResponseEntity<UserDto> sighUp(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        UserDto userDto1 = userService.toDto(user);
        
        return ResponseEntity.ok(userDto1);
    }

    //관리자 전용 회원 삭제
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok().body("삭제 완료");
    }

    //관리자 전용 회원 전부 리턴
    @GetMapping("/users/alluser")
    public ResponseEntity<List<UserDto>> allUser() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userService.toDtoList(userList);

        return ResponseEntity.ok(userDtoList);
    }

    //관리자 전용 회원 수정 완료
    @PutMapping("/users/edit/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        System.out.println(userDto.getUserId());
        User user = userService.editUser(id, userDto);
        UserDto userDto1 = userService.toDto(user);

        return ResponseEntity.ok(userDto1);
    }

    //기본키 id를 통해 유저 정보 리턴
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> idUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        UserDto userDto = userService.toDto(user);

        return ResponseEntity.ok(userDto);
    }

    //내정보 리턴
    @GetMapping("/users/mypage")
    public ResponseEntity<UserDto> myPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).get();
        System.out.println("유저id" + user.getUserId());
        UserDto userDto = userService.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    //내정보 수정
    @PutMapping("/users/mypage")
    public ResponseEntity<UserDto> myPageEdit(@RequestBody UserDto userDto) {
        User user = userService.editUser(userDto.getId(), userDto);

        UserDto userDto1 = userService.toDto(user);

        return ResponseEntity.ok(userDto1);
    }

    // 아이디 중복 체크 버튼
    @PostMapping("/idcheck")
    public ResponseEntity<?> IdCheck(@RequestBody UserDto userDto) {
//        System.out.println("유저의 아이디" + userDto.getUserId());
        Optional<User> optionalMember = userRepository.findByUserId(userDto.getUserId());
        if (optionalMember.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } return ResponseEntity.ok("성공");

    }

    @PostMapping("/inlogin")
    public ResponseEntity<?> inloing(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getUser().getId());
        if (user.isEmpty()) {
            // 유저가 존재하지 않는 경우
            return ResponseEntity.badRequest().body("유저가 존재하지 않습니다.");
        } else {
            // 유저가 존재하는 경우
            User user1 = user.get();
            return ResponseEntity.ok(userService.toDto(user1));
        }
    }






}
