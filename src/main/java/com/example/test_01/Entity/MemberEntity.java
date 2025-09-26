package com.example.test_01.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "testtable03")
public class MemberEntity {
    @Id
    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String nickname;

    @Column
    private String phone;

    @Column
    private String madress;

    public MemberEntity() {}
}
