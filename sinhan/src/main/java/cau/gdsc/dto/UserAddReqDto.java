package cau.gdsc.dto;

import cau.gdsc.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAddReqDto {
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;
}
