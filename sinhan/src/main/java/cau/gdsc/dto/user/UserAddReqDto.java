package cau.gdsc.dto.user;

import cau.gdsc.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "회원 추가 요청 데이터")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserAddReqDto {
    @ApiModelProperty(value = "회원 이름", example = "홍길동", required = true)
    private String name;
    @ApiModelProperty(value = "키", example = "180", required = true)
    private int height;
    @ApiModelProperty(value = "몸무게", example = "75", required = true)
    private int weight;
    @ApiModelProperty(value = "성별(0: 남성, 1: 여성)", example = "0", required = true)
    private int gender;
    @ApiModelProperty(value = "나이", example = "30", required = true)
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
