package com.sparta.cloud.movie_reservation_auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponse {
    private Long userId;
    private String email;
    private String userName;
    private String birthday;
    private String phoneNumber;
    private String gender;
    private UserRole role;
}
