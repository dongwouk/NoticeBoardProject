package com.example.noticeboardprj.service;

import com.example.noticeboardprj.dto.request.BoardDTO;
import com.example.noticeboardprj.entity.BoardEntity;
import com.example.noticeboardprj.entity.UserEntity;
import com.example.noticeboardprj.repository.BoardRepository;
import com.example.noticeboardprj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;



    public void createBoard(BoardDTO boardDTO, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        UserEntity user = userRepository.findByUsername(authentication.getName());
        // 인증 정보에서 사용자 ID를 가져옵니다.


        BoardEntity boardEntity = BoardEntity.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user) // 인증된 사용자 설정
                .build();

        boardRepository.save(boardEntity);
    }




//    public List<BoardDTO> BoardList() {
//
//        return boardRepository.findAllByOrderByCreatedAtDesc().stream()
//                .map(BoardDTO::new)
//                .toList();
//    }
}
