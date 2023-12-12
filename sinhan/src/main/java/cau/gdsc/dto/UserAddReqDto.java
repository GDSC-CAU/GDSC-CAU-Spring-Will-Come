package cau.gdsc.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAddReqDto {
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;
}
