package com.example.test_01.Entity;

import jakarta.persistence.*;
import lombok.*;

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
    long testnum;

    @Column
    int testvalue01;

    @Column
    String testvalue02;

    public TestEntity() {

    }
}
