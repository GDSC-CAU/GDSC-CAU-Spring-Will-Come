package cau.gdsc.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserUpdateReqDto {
    private int height;
    private int weight;
    private Long userId;
}
