package com.example.demo.persistence;

import com.example.demo.medel.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "select * from Todo t where t.USERID = ?1", nativeQuery = true)
    List<BoardEntity> findByUserId(String userId);
}
