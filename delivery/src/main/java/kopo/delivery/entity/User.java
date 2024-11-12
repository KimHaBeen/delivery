package kopo.delivery.entity;

import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "USER")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userID;

    @Column(name = "user_password", nullable = false)
    private String userPW;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_phone", nullable = false, length = 13)
    private int userPhone;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    //카카오 필드
    @Column(name = "nickname", nullable = true)
    private String nickname; // 카카오 로그인 닉네임을 저장하기 위한 필드

    @Column(name = "provider", nullable = true)
    private String provider; // 소셜 로그인 제공자 (예: "kakao")

    @Column(name = "provider_id", nullable = true)
    private String providerId;

}
