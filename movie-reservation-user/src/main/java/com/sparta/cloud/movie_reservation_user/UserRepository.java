package com.sparta.cloud.movie_reservation_user;

import com.sparta.cloud.movie_reservation_user.core.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotNull(message = "이메일은 필수 값입니다.") @Email(message = "양식을 지켜 작성해주세요 email@domain.com") String email);
}
