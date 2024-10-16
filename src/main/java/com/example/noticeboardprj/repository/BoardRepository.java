package com.example.noticeboardprj.repository;

import com.example.noticeboardprj.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("SELECT b FROM BoardEntity b WHERE b.deleted = false ORDER BY b.createdDate DESC")
    List<BoardEntity> findAllByOrderByCreatedDateDesc();

    Optional<BoardEntity> findById(Long id);  // 게시글 ID로 게시글 조회
    Optional<BoardEntity> findByIdAndUserId(Long id, Long userId);  // 특정 사용자에 대한 특정 게시글 조회
}
