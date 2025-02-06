package com.sparta.cloud.movie_reservation_auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public TokenResponse getToken(
            @RequestBody UserRequest userRequest
    ) {
         return authService.createToken(userRequest);
    }
}
