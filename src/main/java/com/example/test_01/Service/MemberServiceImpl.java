package com.example.test_01.Service;

import com.example.test_01.DTO.MemberDTO;
import com.example.test_01.Entity.MemberEntity;
import com.example.test_01.Repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(MemberDTO dto) {
        MemberEntity entity = new MemberEntity();
        entity.setId(dto.getId());
        entity.setPw(passwordEncoder.encode(dto.getPw()));
        entity.setNickname(dto.getNickname());
        entity.setPhone(dto.getPhone());
        entity.setMadress(dto.getMadress());
        if (dto.getRole() != null && !dto.getRole().isEmpty()) {
            entity.setRole(dto.getRole());
        }
        repository.save(entity);
    }

    @Override
    public void saveAdmin(MemberDTO dto) {
        dto.setRole("ADMIN");
        save(dto);
    }

    @Override
    public int nickcheck(String nickname) {
        return repository.nickcheck(nickname);
    }

    @Override
    public int phonecheck(String phone) {
        return repository.phonecheck(phone);
    }

    @Override
    public int idcehck(String id) {
        return repository.idcheck(id);
    }
}
