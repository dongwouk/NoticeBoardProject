package com.example.noticeboardprj.repository;

import com.example.noticeboardprj.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByOrderByCreatedAtDesc();
    Optional<BoardEntity> findByIdAndUserId(Long id, Long userId);
}
