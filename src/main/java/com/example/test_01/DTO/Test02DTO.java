package com.example.test_01.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Test02DTO {
    private long id;
    private String title;
    private String content;
    private String writer;
    private LocalDate inputdate;

}
