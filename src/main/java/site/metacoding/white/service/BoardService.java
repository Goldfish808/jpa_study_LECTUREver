package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto.UserDto;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {

        Board boardPS = boardRepository.save(boardSaveReqDto.toEntity());

        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

        return boardSaveRespDto;
    }

    @Transactional(readOnly = true) // 세션 종료 안됨
    public Board findById(Long id) {
        System.out.println("최초 select");
        Board boardPS = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료3
        System.out.println("두번째 select");
        boardPS.getUser().getUsername(); // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번
        // 4. user select 됨?
        System.out.println("서비스단에서 지연로딩 함. 왜? 여기까지는 디비커넥션이 유지되니까");
        return boardPS;
    }

    @Transactional
    public void update(Long id, Board board) {
        Board boardPS = boardRepository.findById(id);
        boardPS.update(boardPS.getTitle(), boardPS.getContent());
    } // 트랜잭션 종료시 -> 더티체킹을 함

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

}
