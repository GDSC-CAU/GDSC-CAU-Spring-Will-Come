package cau.gdsc.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;

    // 정적 팩토리 메서드. DTO를 파라미터로 할 수 있지만 DTO의 생성자도 PRIVATE으로 설정했기 때문에 테스트가 어렵다.
    @Builder
    public static User of(String name, int height, int weight, int gender, int age) {
        User user = new User();
        user.name = name;
        user.height = height;
        user.weight = weight;
        user.gender = gender;
        user.age = age;
        return user;
    }

    // 필요한 update 정보만 받아와 수정
    public void update(int height, int weight){
        this.height = height;
        this.weight = weight;
    }
}