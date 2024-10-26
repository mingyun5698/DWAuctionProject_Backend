package com.example.AuctionProject.user.service;

import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.user.dto.UserDto;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // 회원가입 메서드
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserType(UserRoleEnum.USER);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setBirthdate(userDto.getBirthdate());
        user.setGender(userDto.getGender());
        user.setContact(userDto.getContact());
        userRepository.save(user);
        
        return user;
        
        
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User editUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).get();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setContact(userDto.getContact());
        user.setBirthdate(userDto.getBirthdate());
        userRepository.save(user);

        return user;
    }



    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserId(user.getUserId());
        userDto.setUserType(user.getUserType());
        userDto.setUsername(user.getUsername());
        userDto.setBirthdate(user.getBirthdate());
        userDto.setContact(user.getContact());
        userDto.setEmail(user.getEmail());
        userDto.setGender(user.getGender());

        return userDto;
    }

    public List<UserDto> toDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user : userList) {
            UserDto userDto = toDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }



    // 비밀번호 찾기
    public Optional<User> findUserByUserId(UserDto userDto) {
        return userRepository.findByUserIdAndEmail(userDto.getUserId(), userDto.getEmail());
    }

    // 이메일로 비밀번호 찾기시 랜덤숫자 저장
    public void passwordResetByEmail(User user, int verificationCode) {
        user.setPassword(passwordEncoder.encode(String.valueOf(verificationCode)));
        userRepository.save(user);
    }

    public List<User> findUsersByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }
}
