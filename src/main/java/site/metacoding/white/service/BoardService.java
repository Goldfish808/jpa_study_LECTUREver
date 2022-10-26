package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardUpdateRespDto;
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
    public BoardDetailRespDto findById(Long id) {
        // Board boardPS = boardRepository.findById(id)
        // .orElseThrow(() -> new RuntimeException("해당 " + id + "로 조회할 수 없습니다"));

        Optional<Board> boardOP = boardRepository.findById(id);

        if (boardOP.isPresent()) { // NULL 이 아니라면 수행하도록 하여 NULLPOINEXCEPTION 을 방지
            BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardOP.get()); // 타입을 위하여 줌
            return boardDetailRespDto;
        } else {
            throw new RuntimeException("해당 " + id + "로 조회할 수 없습니다");
        }
        // BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardPS);

        // System.out.println("최초 select");
        // Board boardPS = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료3
        // System.out.println("두번째 select");
        // boardPS.getUser().getUsername(); // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번
        // // 4. user select 됨?
        // System.out.println("서비스단에서 지연로딩 함. 왜? 여기까지는 디비커넥션이 유지되니까");
        // return boardDetailRespDto;
    }

    @Transactional
    public BoardUpdateRespDto update(BoardUpdateReqDto boardUpdateReqDto) {
        Long id = boardUpdateReqDto.getId();
        Optional<Board> boardOP = boardRepository.findById(id);

        if (boardOP.isPresent()) {
            Board boardPS = boardOP.get();
            boardPS.update(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent());
            return new BoardUpdateRespDto(boardPS);
        } else {
            throw new RuntimeException("해당 " + id + "로 조회할 수 없습니다");
        }

        // Board boardPS = boardRepository.findById(id);
        // boardPS.update(boardPS.getTitle(), boardPS.getContent());

    } // 트랜잭션 종료시 -> 더티체킹을 함

    @Transactional(readOnly = true)
    public List<BoardAllRespDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardAllRespDto> boardAllList = new ArrayList<>();
        // 1. List 크기만큼 for문 돌리기
        for (Board list : boardList) {
            // 2. board -> DTO 로 옮기기
            // BoardAllRespDto boardAllRespDto = new BoardAllRespDto(list);
            // 3. DTO 를 LIST 에 담기
            boardAllList.add(new BoardAllRespDto(list));

        }

        return boardAllList;
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

}
