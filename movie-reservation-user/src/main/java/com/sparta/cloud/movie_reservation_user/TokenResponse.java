package com.sparta.cloud.movie_reservation_user;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
