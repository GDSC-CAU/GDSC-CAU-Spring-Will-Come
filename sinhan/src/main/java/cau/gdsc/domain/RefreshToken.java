package cau.gdsc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 리프레시 토큰은 토큰 탈취를 방지하기 위한 하나의 수단이다.
// 액세스 토큰이 만료되면 리프레시 토큰을 이용해 액세스 토큰을 갱신한다. 리프레시 토큰도 갱신하면 보안이 높아진다.
// 일종의 세션처럼 리프레시 토큰은 DB에서도 관리한다.
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {
    @Id
    @Column(name = "token_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private RefreshToken(String token) {
        this.token = token;
    }
}
