package com.sparta.cloud.movie_reservation_user;

import com.sparta.cloud.movie_reservation_user.core.User;
import com.sparta.cloud.movie_reservation_user.core.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthClient authClient;

    @Transactional
    public void signUp(UserSignUpRequest userSignUpRequest) {
        userRepository.save(User.fromSignUpRequest(userSignUpRequest));
    }

    @Transactional
    public TokenResponse signIn(UserSignInRequest userSignInRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        TokenResponse token;

        User user = userRepository.findByEmail(userSignInRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("아이디 또는 비밀번호를 다시 확인해주세요")
        );

        if(passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())) {
            UserResponse response = User.toResponse(user);
            token = authClient.getToken(response);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.updateRefreshToken(token.getRefreshToken());

        return token;

    }

    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        user.updateRefreshToken(null);
    }
}
