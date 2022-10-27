package site.metacoding.white.web;

import java.net.HttpURLConnection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    // @Autowired
    // private UserService userService;

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper(); // json
        headers = new HttpHeaders(); // http 요청 header에 필요
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void join_test() throws JsonProcessingException {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("hoho2");
        joinReqDto.setPassword("1234");

        String body = om.writeValueAsString(joinReqDto);
        System.out.println(body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST, request, String.class);

        // then
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getBody());

        DocumentContext dc = JsonPath.parse(response.getBody());
        System.out.println(dc.jsonString());
        Integer code = dc.read("$.code");
        Assertions.assertThat(code).isEqualTo(1);
    }

}