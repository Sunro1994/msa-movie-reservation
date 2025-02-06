package com.sparta.cloud.movie_reservation_user;

import com.sparta.cloud.movie_reservation_user.core.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpRequest {

    @Email //^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$
    @NotNull(message = "이메일은 필수 값입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private String password;

    @Size(min = 3, max = 50)
    private String userName;

    @NotNull
    private UserRole role;

    private String phoneNumber;

    private String gender;

    private String birthday;

}
