package site.metacoding.white.web;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.service.BoardService;
import site.metacoding.white.service.UserService;
import site.metacoding.white.util.SHA256;

@ActiveProfiles("test")
@Sql("classpath:truncate.sql") // ????????? ?????? ????????? truncate.sql ??????????????? ??????????????? ??? ????????????
@Transactional // ???????????? ???????????? ????????? ?????????????????? DB??? flush ?????? (Hibernate ?????????)
@AutoConfigureMockMvc // MockMvc Ioc ??????????????? ??????
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SHA256 sha256;

    private MockHttpSession session;

    private static HttpHeaders headers;

    String APPLICATION_JSON = "application/json; charset=utf-8";

    @BeforeEach // ????????? ?????? new ??????
    public void sessionInit() {
        session = new MockHttpSession();
        User user = User.builder().id(1L).username("ssar").build();
        session.setAttribute("sessionUser", new SessionUser(user)); // ???????????? ?????? ?????? ????????? session ??? ??????
    }

    @BeforeEach
    public void dataInit() {
        String encPassword = sha256.encrypt("1234");
        User user = User.builder().username("ssar").password(encPassword).build();
        User userPS = userRepository.save(user);

        Board board = Board.builder()
                .title("?????????1???")
                .content("??????????????????")
                .user(userPS)
                .build();
        Board boardPS = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("???????????????")
                .board(boardPS)
                .user(userPS)
                .build();

        Comment comment2 = Comment.builder()
                .content("???????????????")
                .board(boardPS)
                .user(userPS)
                .build();

        commentRepository.save(comment);
        commentRepository.save(comment2);
    }

    @Test
    public void findById_test() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/board/" + id).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void save_test() throws Exception {
        // given
        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle("?????????1???");
        boardSaveReqDto.setContent("??????????????????");

        String body = om.writeValueAsString(boardSaveReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/board").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .session(session));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void findAll_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/board").accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1L));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].title").value("?????????1???"));
    }

    @Test
    public void update_test() throws Exception {
        // given
        BoardUpdateReqDto boardUpdateReqDto = new BoardUpdateReqDto();

        Long id = 1L;
        boardUpdateReqDto.setTitle("????????? 2 ???");
        boardUpdateReqDto.setContent("JUnit ??????");

        String body = om.writeValueAsString(boardUpdateReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/board").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .session(session));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteById_test() throws Exception {
        // given
        BoardUpdateReqDto boardUpdateReqDto = new BoardUpdateReqDto();

        Long id = 1L;
        boardUpdateReqDto.setTitle("????????? 2 ???");
        boardUpdateReqDto.setContent("JUnit ??????");

        String body = om.writeValueAsString(boardUpdateReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/board").content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .session(session));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("????????? : " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void _test() {
        // given

        // when

        // then

    }

}
