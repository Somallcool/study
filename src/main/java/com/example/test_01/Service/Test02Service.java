package com.example.test_01.Service;

import com.example.test_01.DTO.Test02DTO;
import com.example.test_01.Entity.Test02Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Test02Service {
    void saveBoard(Test02DTO dto);

    List<Test02Entity> outBoard(int page, int size);

    int getTotalPages(int size);

    Test02Entity update02(long num);

    void update_save2(Test02Entity Entity);

    void delete02(long num);

    Test02Entity detail02(long num);

    List<Test02Entity> titleSearch(String testsearch);
}
