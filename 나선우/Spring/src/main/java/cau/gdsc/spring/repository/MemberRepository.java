package cau.gdsc.spring.repository;

import cau.gdsc.spring.domain.Member;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository 계층은 실제로 DB에 접근하는 계층입니다.
 * 단순하게 CRUD 기능만 필요하다면 CrudRepository를 상속받아서 사용해도 충분합니다.
 * 다만 고급 기능 (정렬, 페이징 등)이 필요하다면 이를 상속받은 PagingAndSortingRepository를 사용하는데,
 * JpaRepository는 바로 이 PagingAndSortingRepository를 상속받기에, 입문 단계인 경우 편하게 JpaRepository를 사용하면 됩니다.
 * 필요한 메서드가 있다면 직접 인터페이스만 선언해주면 JPA가 알아서 구현체를 만들어서 이를 사용할 수 있도록 합니다.
 * 즉, 일종의 기능 명세서라고 생각하면 됩니다.
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id); // id로 회원 찾기
    Optional<Member> findByName(String name); // 없으면 null이 반환되는데, 그렇게 반환하지 않고 Optional로 감싸서 반환한다.
    List<Member> findAll(); // 지금까지 저장된 모든 회원 리스트로 반환
    boolean existsByName(String name); // 이름으로 회원 찾기
}