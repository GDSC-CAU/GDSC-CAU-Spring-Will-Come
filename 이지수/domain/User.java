package domain;

@Entity
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    private String name; // 닉네임

    private int height; // 키

    private int weight; // 몸무게

    private int gender; // 성별 (0, 1)

    private int age;

    public User(String name, int height, int weight, int gender, int age) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
    }
}
