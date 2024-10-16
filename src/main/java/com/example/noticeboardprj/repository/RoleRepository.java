package com.example.noticeboardprj.repository;

import com.example.noticeboardprj.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name); // existsByName 메소드 추가
}
