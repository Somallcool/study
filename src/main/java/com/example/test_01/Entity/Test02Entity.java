package com.example.test_01.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@SequenceGenerator(name = "board", sequenceName = "test_board0919",allocationSize = 1)
@Table(name = "testtable02")
public class Test02Entity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board")
    private long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String writer;

    @Column
    private LocalDate inputdate;

    @Column
    private String testimage;

    public Test02Entity() {}
}
