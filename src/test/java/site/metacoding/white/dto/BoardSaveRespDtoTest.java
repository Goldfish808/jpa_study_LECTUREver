package site.metacoding.white.dto;

import org.junit.jupiter.api.Test;

import site.metacoding.white.domain.Board;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

public class BoardSaveRespDtoTest {

    @Test
    public void innerclass_테스트() {
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(new Board());
    }
}
