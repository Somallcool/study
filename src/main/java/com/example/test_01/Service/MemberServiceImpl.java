package com.example.test_01.Service;

import com.example.test_01.DTO.MemberDTO;
import com.example.test_01.Entity.MemberEntity;
import com.example.test_01.Repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;

    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(MemberDTO dto) {
        MemberEntity entity = new MemberEntity();
        entity.setId(dto.getId());
        entity.setPw(dto.getPw());
        entity.setNickname(dto.getNickname());
        entity.setPhone(dto.getPhone());
        entity.setMadress(dto.getMadress());

        repository.save(entity);
    }

    @Override
    public int idcehck(String id) {
        return repository.idcheck(id);
    }
}
