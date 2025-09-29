package com.example.test_01.Controller;

import com.example.test_01.DTO.MemberDTO;
import com.example.test_01.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @Autowired
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/sign_up")
    public String signup01(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/signup";
    }

    @PostMapping("/signup_save")
    public String signup02(MemberDTO dto) {
        if(!dto.getPw().equals(dto.getPwcheck())) {
            return "member/signup";
        }

        service.save(dto);
        return "redirect:/";
    }

/*
    @PostMapping("/signup_save")
    public String signup02(@Valid MemberDTO dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        service.save(dto);
        return "redirect:/";
    }

 */

    @PostMapping("/check/id")
    @ResponseBody
    public String idcheck(@RequestParam("id") String id) {
        int count = service.idcehck(id);

        if (count == 0) {
            return "ok";
        } else {
            return "no";
        }
    }

    @PostMapping("/check/pw")
    @ResponseBody
    public String pwcheck(@RequestParam("pw") String pw,
                          @RequestParam("pwCheck") String pwCheck) {

        if(pw.equals(pwCheck)) {
            return "match";
        } else {
            return "nomatch";
        }
    }

    @PostMapping("/check/nickname")
    @ResponseBody
    public String nickcheck(@RequestParam("nickname") String nickname){
        int count = service.nickcheck(nickname);
        if (count == 0) {
            return "match";
        } else {
            return "no";
        }
    }

    @PostMapping("/check/phone")
    @ResponseBody
    public String phonecheck(@RequestParam("phone") String phone){
        int count = service.phonecheck(phone);
        if (count == 0) {
            return "match";
        } else {
            return "no";
        }
    }

}
