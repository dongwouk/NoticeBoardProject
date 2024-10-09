package com.example.noticeboardprj.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
//
@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
    private String email;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private LocalDateTime removed_date;



}
