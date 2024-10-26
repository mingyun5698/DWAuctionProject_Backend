package com.example.AuctionProject.user.dto;

import com.example.AuctionProject.auth.security.UserRoleEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userId;
    private String username;
    private String password;
    private String confirmPassword;
    private String birthdate;
    private String gender;
    private String email;
    private String contact;
    private UserRoleEnum UserType;


}
