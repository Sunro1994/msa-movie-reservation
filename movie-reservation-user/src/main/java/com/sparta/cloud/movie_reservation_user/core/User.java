package com.sparta.cloud.movie_reservation_user.core;

import com.sparta.cloud.movie_reservation_user.UserSignUpRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLRestriction("is_deleted = false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    private String gender;

    private String phoneNumber;

    @Column(nullable = false)
    private String birthday;

    private UserRole role;

    @SoftDelete(columnName = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    private String refreshToken;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public static User fromSignUpRequest(UserSignUpRequest userSignUpRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .email(userSignUpRequest.getEmail())
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .userName(userSignUpRequest.getUserName())
                .birthday(userSignUpRequest.getBirthday())
                .role(userSignUpRequest.getRole())
                .phoneNumber(userSignUpRequest.getPhoneNumber())
                .gender(userSignUpRequest.getGender())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .role(user.getRole())
                .build();
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void withDrawUser() {
        this.isDeleted = Boolean.TRUE;
    }
}
