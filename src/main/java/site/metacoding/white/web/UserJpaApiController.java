package site.metacoding.white.web;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserJpaRepository;
import site.metacoding.white.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class UserJpaApiController {

    private final UserJpaRepository userJpaRepository;

    @GetMapping("/jpa/user/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {
        Optional<User> userOP = userJpaRepository.findById(id);
        if (userOP.isPresent()) {
            return new ResponseDto<>(1, "성공", userOP);
        }
        return null;
    }

    @PostMapping("/jpa/join")
    public ResponseDto<?> save(@RequestBody User user) {
        User userPS = userJpaRepository.save(user);
        return new ResponseDto<>(1, "성공", userPS);
    }

    @PostMapping("/jpa/login")
    public ResponseDto<?> login(@RequestBody User user) {
        User userPS = userJpaRepository.findByUsername(user.getUsername());
        if (userPS.getPassword().equals(user.getPassword())) {
            return new ResponseDto<>(1, "성ㄱ오", userPS);
        }
        return new ResponseDto<>(-1, "해당 계정의 아이디나 비밀번호 오류가 있음", user.getUsername());
    }

    @GetMapping("/jpa/user")
    public ResponseDto<?> findAll(Integer page) {
        PageRequest pageRequestTR = PageRequest.of(page, 2);
        // List<User> userList = userJpaRepository.findAll();
        Page<User> userList = userJpaRepository.findAll(pageRequestTR);
        return new ResponseDto<>(1, "성공", userList);
    }

}
