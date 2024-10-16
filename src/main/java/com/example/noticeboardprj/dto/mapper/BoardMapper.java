package com.example.noticeboardprj.dto.mapper;

import com.example.noticeboardprj.dto.dto.BoardResponseDTO;
import com.example.noticeboardprj.entity.BoardEntity;

public class BoardMapper {

    // Entity -> ResponseDTO 변환
    public static BoardResponseDTO toResponseDTO(BoardEntity entity) {
        return new BoardResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUser().getUsername(),
                entity.getCreatedDate()
        );
    }
}