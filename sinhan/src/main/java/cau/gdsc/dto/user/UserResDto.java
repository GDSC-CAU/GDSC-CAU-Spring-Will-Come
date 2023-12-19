package cau.gdsc.dto.user;

import cau.gdsc.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserResDto {
    private Long id;
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;

    public static UserResDto of(User user) {
        return UserResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .height(user.getHeight())
                .weight(user.getWeight())
                .gender(user.getGender())
                .age(user.getAge())
                .build();
    }
}
