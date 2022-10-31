package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.util.SHA256;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SHA256 sha256;

    // 응답의 DTO는 서비스에서 만든다.
    @Transactional // 트랜잭션이 붙이지 않으면 영속화 되어 있는 객체가 flush가 안됨.
    public JoinRespDto save(JoinReqDto joinReqDto) {

        // 비밀번호 해시
        String encPassword = sha256.encrypt(joinReqDto.getPassword());
        // 해시된 비밀번호로 DTO 의 password값 넣기
        joinReqDto.setPassword(encPassword);

        User userPS = userRepository.save(joinReqDto.toEntity());
        return new JoinRespDto(userPS);
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginReqDto loginReqDto) {

        String encPassword = sha256.encrypt(loginReqDto.getPassword());

        // Optional<User> userPS =
        // userRepository.findByUsername(loginReqDto.getUsername());
        // if (userPS.get().getPassword().equals(encPassword)) {
        // return new SessionUser(userPS.get());
        // } else {
        // throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        // }

        Optional<User> userOP = userRepository.findByUsername(loginReqDto.getUsername());
        if (userOP.isEmpty()) {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }

        User userPS = userOP.get();
        if (!userPS.getPassword().equals(encPassword)) {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }

        return new SessionUser(userPS);

    } // 트랜잭션 종료

}
