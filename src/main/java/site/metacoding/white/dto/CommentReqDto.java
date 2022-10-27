package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;

public class CommentReqDto {

    @Getter
    @Setter
    public static class CommentSaveReqDto {
        private String content;
        private SessionUser sessionUser; // 서비스의 로직, 로그인 된 상태에서만 가능하도록
        private Long boardId;
        // Board와 관련된 객체도 필요함... 일단 메모만 해두기로 함

        public Comment toEntity(Board board) {
            return Comment.builder()
                    .content(content)
                    .board(board)
                    .user(sessionUser.toEntity()).build();
        }
    }

}
