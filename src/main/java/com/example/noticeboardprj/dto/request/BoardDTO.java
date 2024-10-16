package com.example.noticeboardprj.dto.request;

import com.example.noticeboardprj.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class BoardDTO {
    private String title;
    private String content;

    @JsonCreator
    public BoardDTO(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

