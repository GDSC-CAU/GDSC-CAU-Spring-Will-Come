package cau.gdsc.spring.service;

import cau.gdsc.spring.domain.Member;
import cau.gdsc.spring.dto.MemberDto;
import cau.gdsc.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원 가입
     * 생성 메서드를 통해 회원 객체를 생성하고, 이를 DB에 저장합니다.
     * 실제 서비스를 만들 때는 특정 값의 중복 여부 등을 판정해야 하는 상황이 존재할 수 있기에 이를 위한 메서드를 별도로 만들어서 사용하는 경우가 많습니다.
     * */
    public Long join(MemberDto memberDto){
        // 같은 이름이 있는 중복 회원은 안됨.
        validateDuplicateMember(memberDto.getName());
        Member member = Member.createMember(memberDto.getName(), memberDto.getEmail()); // 멤버 객체 생성
        return memberRepository.save(member).getId(); // 멤버 객체 DB 테이블에 저장하고, 이 멤버의 고유식별자 반환
    }

    /**
     * 중복 회원 검증용 메서드
     * 이름을 통해 중복 여부를 검증하는 내부 메서드이기에, private을 사용했습니다.
     * 존재 여부만 판정할 때는 existsBy 메서드를 사용하는 것이 서버 리소스를 아낄 수 있습니다.
     * 그러나 실제로 member 객체를 가져와야 하는 경우에는 findBy 메서드를 사용해야 합니다.
     * 만약 직접 객체를 가져와야 하는 경우엔 validate보다는 getMemberBy~와 같은 메서드명으로 작성하는 것이 좋습니다.
     */
    private void validateDuplicateMember(String name) {
        if(memberRepository.existsByName(name)){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        /*memberRepository.findByName(name).ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
    }

    /**
     * 전체 회원 조회
     * findAll()은 특정 테이블에 존재하는 모든 데이터를 조회하는 메서드입니다.
     * 다만 모든 데이터를 가져오는 만큼 서버에 부하가 걸리기 때문에, 실제로 사용할 때는 페이징(10개씩) 처리를 해서 사용하는 것이 좋습니다.
     * 페이징의 경우에는 추후 기회가 된다면 다루도록 하겠습니다.
     * */
    public List<MemberDto> findMembers(){
        List<Member> members = memberRepository.findAll();
        /*List<MemberDto> memberDtos = new ArrayList<>();
        for(Member member : members){
            memberDtos.add(MemberDto.from(member));
        }
        return memberDtos;*/
        // 객체를 Dto로 바꾸는 위 for문은 아래 stream을 사용한 코드와 같은 결과를 반환합니다. stream을 통해 코드를 간결하게 만들 수 있습니다.
        return members.stream().map(MemberDto::from).collect(Collectors.toList());
    }

    /**
     * 회원 한명 조회 (Optional)
     * Optional은 null이 반환될 가능성이 있는 객체를 감싸서 NullPointException을 방지하기 위해 사용합니다.
     * 다만 Optional을 사용하면 성능이 느려질 수 있기에, 실전에서는 Optional 없이 orElseThrow()를 사용하는 경우가 많습니다.
     * */
    public Optional<MemberDto> findOneOptional(Long memberId){
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        // Optional.map()은 Optional 객체가 존재할 경우에만 실행되며, 존재하지 않을 경우에는 실행되지 않고 빈 Optional 객체를 반환합니다.
        return memberOpt.map(MemberDto::from);
    }

    /**
     * 회원 한명 조회 (Optional 없이 orElseThrow() 사용)
     * 보다 실전적인 코드이며, 클라이언트에게 빈 값을 반환하는 게 의미가 없는 상황일 경우 사용합니다.
     * orElseThrow() 메서드를 사용하면, 값이 없는 경우에는 Optional처럼 빈 값을 반환하지 않고 Exception을 발생시킵니다.
     * 다만 실제로 findBy를 통해 Repository 계층에서 뽑아내는 객체 자체는 여전히 Optional로 감싸져 있습니다.
     * */
    public MemberDto findOne(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        return MemberDto.from(member);
    }
}