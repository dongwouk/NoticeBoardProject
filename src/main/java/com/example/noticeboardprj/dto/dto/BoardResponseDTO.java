package com.example.noticeboardprj.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;

}
