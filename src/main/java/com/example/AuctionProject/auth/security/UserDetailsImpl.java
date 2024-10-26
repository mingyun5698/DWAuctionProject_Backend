package com.example.AuctionProject.auth.security;

import com.example.AuctionProject.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final String password;
    private final String userId;

    public UserDetailsImpl(User user, String password, String userId) {
        this.user = user;
        this.password = password;
        this.userId = userId;
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum usertype = user.getUserType();
        String authority = usertype.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }/*false: 사용자 계정의 유효 기간 만료*/

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }/*false: 계정 잠금 상태*/

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }/*false: 비밀번호 만료*/

    @Override
    public boolean isEnabled() {
        return true;
    } /*false: 유효하지 않은 사용자*/
}
