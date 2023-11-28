package cau.gdsc.spring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 구분하기 위해 시스템이 저장하는 아이디

    private String name;

    private String email;

    // 생성 메서드
    public static Member createMember(String name, String email) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        return member;
    }

    // 수정 메서드
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}