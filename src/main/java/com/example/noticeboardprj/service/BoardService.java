package com.example.noticeboardprj.service;

import com.example.noticeboardprj.dto.dto.BoardDTO;
import com.example.noticeboardprj.dto.dto.BoardResponseDTO;
import com.example.noticeboardprj.dto.mapper.BoardMapper;
import com.example.noticeboardprj.entity.BoardEntity;
import com.example.noticeboardprj.entity.UserEntity;
import com.example.noticeboardprj.repository.BoardRepository;
import com.example.noticeboardprj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 조회
    public List<BoardResponseDTO> getAllBoards() {
        List<BoardEntity> boardEntities = boardRepository.findAllByOrderByCreatedDateDesc();
        return boardEntities.stream()
                .map(BoardMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 특정 게시글 조회
    public BoardResponseDTO getBoardById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));
        return BoardMapper.toResponseDTO(boardEntity);
    }

    // 게시글 생성
    public void createBoard(BoardDTO boardDTO, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        UserEntity user = userRepository.findByUsername(authentication.getName());
        // 인증 정보에서 사용자 ID를 가져옵니다.

        BoardEntity boardEntity = BoardEntity.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .user(user) // 인증된 사용자 설정
                .build();

        boardRepository.save(boardEntity);
    }

    public void updateBoard(Long boardId, BoardDTO boardDTO, Authentication authentication) {
        // 인증 정보가 없는 경우 예외 발생
        if (authentication == null) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // 현재 로그인한 사용자 정보 가져오기
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        // 게시글이 해당 사용자의 것인지 확인
        BoardEntity boardEntity = boardRepository.findByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없거나 수정 권한이 없습니다."));

        // 게시글 수정
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());

        boardRepository.save(boardEntity);
    }

    // 게시글 논리적 삭제
    public void deleteBoard(Long boardId, Authentication authentication) {
        // 인증 정보가 없는 경우 예외 발생
        if (authentication == null) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // 현재 로그인한 사용자 정보 가져오기
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        // 게시글이 해당 사용자의 것인지 확인
        BoardEntity boardEntity = boardRepository.findByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없거나 삭제 권한이 없습니다."));

        // 논리적 삭제 수행
        boardEntity.delete();

        // 업데이트 후 저장
        boardRepository.save(boardEntity);
    }

}
