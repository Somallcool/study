package com.example.test_01.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@SequenceGenerator(
        name = "test",
        sequenceName = "test_seq",
        allocationSize = 1
)
@Table(name = "testtable01")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test")
    @Column
    private long testnum;

    @Column
    private int testvalue01;

    @Column
    private String testvalue02;

    @Column
    private LocalDate inputdate;

    public TestEntity() {}
}
