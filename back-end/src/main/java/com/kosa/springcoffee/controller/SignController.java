package com.kosa.springcoffee.controller;

import com.kosa.springcoffee.dto.SignInDTO;
import com.kosa.springcoffee.dto.SignUpDTO;
import com.kosa.springcoffee.entity.Member;
import com.kosa.springcoffee.repository.MemberRepository;
import com.kosa.springcoffee.security.service.MemberUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.kosa.springcoffee.entity.MemberRole.ADMIN;
import static com.kosa.springcoffee.entity.MemberRole.USER;
@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v0")
public class SignController {
    private final MemberUserDetailsService memberUserDetailService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signUp")
    public void signUpForm(){
        log.info("회원가입--------------------------");
    }

    @RequestMapping("/login")
    public String signInForm(){
        return "login";
    }
    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody SignInDTO signInDTO){
        Member member = memberRepository.findByEmail(signInDTO.getEmail());
        if (member == null){
            return new ResponseEntity<String>("아이디가 존재하지 않습니다.", HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(signInDTO.getPassword(), member.getPassword())){
            return new ResponseEntity<String>("비밀번호가 틀립니다.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Member>(member, HttpStatus.OK);





    }


    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpDTO signUpDTO){
        Member member = Member.builder()
                .email(signUpDTO.getEmail())
                .fromSocial(false)
                .password(signUpDTO.getPassword())
                .name(signUpDTO.getName())
                .build();

        if (signUpDTO.getIsAdmin() == 1){
            member.addMemberRole(ADMIN);
        }
        member.addMemberRole(USER);
        try {
            memberUserDetailService.joinMember(member);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("회원가입이 실패하였습니다", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<String>("회원가입 성공",HttpStatus.OK);
    }
}
