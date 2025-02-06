package com.sparta.cloud.movie_reservation_auth;

import lombok.Data;

@Data
public class UserRequest {
    private Long userId;
    private String email;
    private String userName;
    private String birthday;
    private String phoneNumber;
    private String gender;
    private UserRole role;
}
