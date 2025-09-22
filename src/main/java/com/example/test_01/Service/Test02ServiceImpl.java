package com.example.test_01.Service;

import com.example.test_01.DTO.Test02DTO;
import com.example.test_01.Entity.Test02Entity;
import com.example.test_01.Repository.Test02Repository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Test02ServiceImpl implements Test02Service{
    private final Test02Repository repository;

    public Test02ServiceImpl(Test02Repository repository) {
        this.repository = repository;
    }

    @Override
    public void saveBoard(Test02DTO dto) {
        Test02Entity entity = new Test02Entity();
        entity.setContent(dto.getContent());
        entity.setTitle(dto.getTitle());
        entity.setWriter(dto.getWriter());
        entity.setInputdate(LocalDate.now());
        entity.setTestimage(dto.getTestimage());

        repository.save(entity);
    }

    @Override
    public List<Test02Entity> outBoard() {
        return repository.findAll();
    }

    @Override
    public Test02Entity update02(long num) {
        return repository.findById(num)
                .orElseThrow(()-> new RuntimeException("아이디를 찾을 수 없습니다."));
    }

    @Override
    public void update_save2(Test02Entity entity) {
        repository.save(entity);
    }

    @Override
    public void delete02(long num) {
        repository.deleteById(num);
    }

    @Override
    public Test02Entity detail02(long num) {
        return repository.findById(num).orElse(null);
    }
}
