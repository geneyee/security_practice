package com.dev.prac.spring0authLogin.controller;

import com.dev.prac.spring0authLogin.dto.MemberJoinDTO;
import com.dev.prac.spring0authLogin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        log.info("join get--------------------------");
        return "/member/join";
    }

    @PostMapping("/join")
    public String join(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes)  {
        log.info("join post-----------------");
        log.info("memberJoinDTO => {}", memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");
//        redirectAttributes.addFlashAttribute("memberJoinDTO", memberJoinDTO);

        return "redirect:/login";
    }

    @GetMapping("/memberProfile")
    public String profile(MemberJoinDTO memberJoinDTO) {
        return "/member/member_profile";
    }

    @GetMapping("/login")
    public String login(String error, String logout) {

        log.info("login get----------------------");
        log.info("logout => {}", logout);

        if (logout != null) {
            log.info("user logout...................");
        }

        return "/member/login";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/index")
    public String index() {
        return "/member/index";
    }


}
