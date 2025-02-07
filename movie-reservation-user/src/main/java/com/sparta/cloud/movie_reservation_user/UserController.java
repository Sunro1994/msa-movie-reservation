package com.sparta.cloud.movie_reservation_user;

import com.sparta.cloud.movie_reservation_user.core.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(
            @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        userService.signUp(userSignUpRequest);

        return ResponseEntity.ok().build();
    }

    //로그인 후 토큰을 발급해서 반환해줘야 함 auth 서버 필요
    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(
            @RequestBody UserSignInRequest userSignInRequest
    ) {
        TokenResponse token = userService.signIn(userSignInRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header("Refresh-Token", token.getRefreshToken())
                .build();
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("X-User-Id") String userId
    ) {
        userService.logout(Long.valueOf(userId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam String phoneNumber
    ) {
        userService.update(userId, phoneNumber);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<UserResponse> getUser(
            @RequestHeader("X-User-Id") String userId
    ) {
        UserResponse myAccount = userService.getMyAccount(userId);

        return ResponseEntity.ok().body(myAccount);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") String userId
    ){
        userService.deleteMyAccount(userId);
        return ResponseEntity.ok().build();
    }

}
