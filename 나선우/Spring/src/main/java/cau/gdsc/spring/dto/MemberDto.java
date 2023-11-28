package cau.gdsc.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MemberDto는 클라이언트가 서버에게 전송하는 요청 데이터, 즉 RequestDto입니다.
 * (다만 이 프로젝트에선 간단한 예제이기에 ResponseDto의 역할도 겸하도록 하겠습니다.)
 * RequestDto는 @Getter와 @NoArgsConstructor가 요구되며, ResponseDto는 @Getter가 요구됩니다.
 */
@Getter
@NoArgsConstructor
public class MemberDto {

    private String name;
    private String email;

    public static MemberDto from(Member member) {
        var memberDto = new MemberDto();
        memberDto.name = member.getName();
        memberDto.email = member.getEmail();
        return memberDto;
    }
}