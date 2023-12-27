package cau.gdsc.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User implements UserDetails { // 스프링 시큐리티 UserDetails 인터페이스를 구현

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;
    private String name;
    private int height;
    private int weight;
    private int gender;
    private int age;
    private String email;
    private String password;

    // 필요한 정보만 값을 넣을 수 있도록
    @Builder
    private User(String name, int height, int weight, int gender, int age, String email, String password) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    // 필요한 update 정보만 받아와 수정
    public void update(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    // 스프링 시큐리티에서 사용하는 메소드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}