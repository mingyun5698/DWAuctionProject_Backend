package com.example.AuctionProject.auth.security;

public enum UserRoleEnum {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN);


    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";

    }

    public static UserRoleEnum fromString(String Usertype) {
        switch (Usertype) {
            case "ROLE_USER":
                return USER;
            case "ROLE_ADMIN":
                return ADMIN;

        }

        return null;
    }
}
