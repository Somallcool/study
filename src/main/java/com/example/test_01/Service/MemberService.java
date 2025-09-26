package com.example.test_01.Service;

import com.example.test_01.DTO.MemberDTO;

public interface MemberService {
    int idcehck(String id);

    void save(MemberDTO dto);
}
