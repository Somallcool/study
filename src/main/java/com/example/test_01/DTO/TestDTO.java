package com.example.test_01.DTO;

import com.example.test_01.Entity.TestEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
public class TestDTO {
    private long testnum;
    private int testvalue01;
    private String testvalue02;
    private LocalDate inputdate;
}
