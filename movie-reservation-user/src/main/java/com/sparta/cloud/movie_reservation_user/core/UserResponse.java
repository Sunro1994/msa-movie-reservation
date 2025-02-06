package com.sparta.cloud.movie_reservation_user.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long userId;
    private String email;
    private String userName;
    private String birthday;
    private String phoneNumber;
    private String gender;
    private UserRole role;

}
