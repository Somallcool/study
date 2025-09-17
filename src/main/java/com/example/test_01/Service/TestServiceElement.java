package com.example.test_01.Service;

import com.example.test_01.DTO.TestDTO;
import com.example.test_01.Entity.TestEntity;
import com.example.test_01.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceElement implements TestService {

    @Autowired
    TestRepository repository;

    @Override
    public void save(TestDTO dto) {
        TestEntity entity = new TestEntity();

        entity.setTestvalue01(dto.getTestvalue01());
        entity.setTestvalue02(dto.getTestvalue02());

        repository.save(entity);

    }

    @Override
    public List<TestEntity> out() {
        return repository.findAll();
    }

    @Override
    public TestEntity update(long num) {
        return repository.findById(num).orElse(null);
    }

    @Override
    public void update_save(TestEntity entity) {
        repository.save(entity);
    }

    @Override
    public void delete(long num) {
        repository.deleteById(num);
    }


}
