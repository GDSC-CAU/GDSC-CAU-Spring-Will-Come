package cau.gdsc.dto.user;

import cau.gdsc.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "회원 조회 응답 데이터")
@Getter // 테스트용 Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserResDto {
    @ApiModelProperty(value = "회원 아이디")
    private Long id;
    @ApiModelProperty(value = "회원 이름", example = "홍길동")
    private String name;
    @ApiModelProperty(value = "키")
    private int height;
    @ApiModelProperty(value = "몸무게")
    private int weight;
    @ApiModelProperty(value = "성별(0: 남자, 1: 여자)", example = "0")
    private int gender;
    @ApiModelProperty(value = "나이")
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
