package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.CommentReqDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.CommentReqDto.CommentSaveReqDto;
import site.metacoding.white.service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final HttpSession session;
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseDto<?> save(@RequestBody CommentSaveReqDto commentSaveReqDto) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 되지 않았습니다");
        }
        commentSaveReqDto.setSessionUser(sessionUser);
        return new ResponseDto<>(1, "성공", commentService.save(commentSaveReqDto));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseDto<?> deleteById(@PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 되지 않았습니다");
        }
        commentService.deleteById(id);
        return new ResponseDto<>(1, "성공", null);
    }

}
