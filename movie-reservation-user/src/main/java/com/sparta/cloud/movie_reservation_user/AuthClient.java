package com.sparta.cloud.movie_reservation_user;

import com.sparta.cloud.movie_reservation_user.core.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/auth/token")
    TokenResponse getToken(@RequestBody UserResponse userRequest);

    @GetMapping("/auth/refresh-token")
    String renewToken(@RequestHeader("refreshToken") String refreshToken);

}
