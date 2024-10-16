package com.example.noticeboardprj.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String title;
    private String content;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private LocalDateTime removed_date;
}

