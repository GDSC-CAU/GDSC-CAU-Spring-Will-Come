package cau.gdsc.spring.controller;

import cau.gdsc.spring.domain.Member;
import cau.gdsc.spring.dto.MemberDto;
import cau.gdsc.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // 멤버 서비스를 스프링이 스프링 컨테이너에 있는 멤버서비스를 가져와서 딱 붙여줌.
    @Autowired
    private MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForms";
    }

    @PostMapping("/members/new")
    public String create(MemberDto memberDto){
        Member member = new Member();
        member.setName(memberDto.getName()); // 서비스
        System.out.println("member= "+ member.getName());
        memberService.join(member);  // 서비스
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }
}