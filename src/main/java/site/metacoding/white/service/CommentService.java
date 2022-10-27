package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.dto.CommentReqDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentRespDto.CommentSaveRespDto;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveRespDto save(CommentSaveReqDto commentSaveReqDto) {

        Optional<Board> boardOP = boardRepository.findById(commentSaveReqDto.getBoardId());
        if (boardOP.isPresent()) {

            Comment comment = commentSaveReqDto.toEntity(boardOP.get());
            // commentRepository.save(comment);

            Comment commentPS = commentRepository.save(comment);

            CommentSaveRespDto commentSaveRespDto = new CommentSaveRespDto(commentPS);
            return commentSaveRespDto;
        } else {
            throw new RuntimeException("게시글이 없네요, 댓글을 작성할 수 없어요");
        }
    }

}
