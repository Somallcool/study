package com.example.test_01.Service;

import com.example.test_01.DTO.TestDTO;
import com.example.test_01.Entity.TestEntity;

import java.util.List;

public interface TestService {

    void save(TestDTO dto);

    List<TestEntity> out();

    TestEntity update(long num);

    void update_save(TestEntity entity);

    void delete(long num);
}
