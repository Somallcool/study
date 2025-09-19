package com.example.test_01.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Test02Entity() {}
}
