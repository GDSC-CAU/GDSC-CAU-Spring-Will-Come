package cau.gdsc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
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

    private User(String name, int height, int weight, int gender, int age) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
    }

    // 정적 팩토리 메서드
    public static User createUser(String name, int height, int weight, int gender, int age) {
        return new User(name, height, weight, gender, age);
    }
}