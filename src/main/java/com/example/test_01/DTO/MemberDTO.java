package com.example.test_01.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MemberDTO {

//    @NotBlank
//    @Size(min = 4, max = 12)
//    @Pattern(regexp = "^[a-zA-Z0-9]", message = "아이디는 영문자와 숫자 조합, 4자 이상 12자 이하만 가능합니다.")
    private String id;
    private String pw;
    private String nickname;
    private String phone;
    private String madress;

}
