package com.sparta.cloud.movie_reservation_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignInRequest {

    @NotNull(message = "이메일은 필수 값입니다.")
    @Email(message = "양식을 지켜 작성해주세요 email@domain.com")
    private String email;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private String password;
}
