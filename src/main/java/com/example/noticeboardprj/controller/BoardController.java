package com.example.noticeboardprj.controller;

import com.example.noticeboardprj.dto.dto.BoardDTO;
import com.example.noticeboardprj.dto.dto.BoardResponseDTO;
import com.example.noticeboardprj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    // 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long id) {
        BoardResponseDTO board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    // 게시글 생성
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createBoard(@ModelAttribute BoardDTO boardDTO, Authentication authentication) {

        System.out.println("asdfassdf");
        if (boardDTO.getContent() == null || boardDTO.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("Content must not be null or empty");
        }

        // 인증된 사용자를 사용하여 게시판 생성
        boardService.createBoard(boardDTO, authentication);
        return ResponseEntity.ok("게시판이 성공적으로 생성되었습니다.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @ModelAttribute BoardDTO boardDTO, Authentication authentication) {
        // 게시글 수정
        boardService.updateBoard(id, boardDTO, authentication);
        return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, Authentication authentication) {
        // 게시글 삭제 로직
        boardService.deleteBoard(id, authentication);
        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }


}
