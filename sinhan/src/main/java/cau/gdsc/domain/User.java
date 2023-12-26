package cau.gdsc.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
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
    private String email;
    private String password;

    // 필요한 정보만 값을 넣을 수 있도록
    @Builder
    private User(String name, int height, int weight, int gender, int age, String email) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    // 필요한 update 정보만 받아와 수정
    public void update(int height, int weight){
        this.height = height;
        this.weight = weight;
    }
}