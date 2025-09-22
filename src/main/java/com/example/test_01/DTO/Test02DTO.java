package com.example.test_01.DTO;

import com.example.test_01.Entity.Test02Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
public class Test02DTO {
    private long id;
    private String title;
    private String content;
    private String writer;
    private LocalDate inputdate;
    private String testimage;

    public Test02DTO(Test02Entity entity){

    }

}
