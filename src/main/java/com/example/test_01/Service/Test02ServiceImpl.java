package com.example.test_01.Service;

import com.example.test_01.DTO.Test02DTO;
import com.example.test_01.Entity.Test02Entity;
import com.example.test_01.Repository.Test02Repository;
import org.springframework.stereotype.Service;

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

        repository.save(entity);
    }
}
