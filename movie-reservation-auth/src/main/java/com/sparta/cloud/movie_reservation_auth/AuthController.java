package com.sparta.cloud.movie_reservation_auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public TokenResponse getToken(
            @RequestBody UserResponse userResponse
    ) {
        log.info("userResponse: {}", userResponse);
         return authService.createToken(userResponse);
    }
}
