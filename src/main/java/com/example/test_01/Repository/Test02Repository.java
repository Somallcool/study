package com.example.test_01.Repository;

import com.example.test_01.Entity.Test02Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Test02Repository extends JpaRepository<Test02Entity, Long> {
    @Query(
            value = "SELECT * FROM ( " +
                    " SELECT a.*, ROWNUM rnum FROM ( " +
                    "   SELECT * FROM testtable02 ORDER BY id DESC " +
                    " ) a WHERE ROWNUM <= :endRow " +
                    ") WHERE rnum > :startRow",
            nativeQuery = true
    )
    List<Test02Entity> findWithPaging(@Param("startRow") int startRow,
                                      @Param("endRow") int endRow);

    @Query(value = "SELECT COUNT(*) FROM testtable02", nativeQuery = true)
    int getTotalCount();
}
