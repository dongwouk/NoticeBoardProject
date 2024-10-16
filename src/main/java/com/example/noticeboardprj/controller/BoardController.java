package com.example.noticeboardprj.controller;

import com.example.noticeboardprj.dto.request.BoardDTO;
import com.example.noticeboardprj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity<String> createBoard(@RequestBody BoardDTO boardDTO, Authentication authentication) {

        if (boardDTO.getContent() == null || boardDTO.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("Content must not be null or empty");
        }

        // 인증된 사용자를 사용하여 게시판 생성
        boardService.createBoard(boardDTO, authentication);
        return ResponseEntity.ok("게시판이 성공적으로 생성되었습니다.");
    }


}
