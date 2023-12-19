package cau.gdsc.dto.user;

import cau.gdsc.domain.User;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserAddReqDto {
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .height(this.height)
                .weight(this.weight)
                .gender(this.gender)
                .age(this.age)
                .build();
    }
}
