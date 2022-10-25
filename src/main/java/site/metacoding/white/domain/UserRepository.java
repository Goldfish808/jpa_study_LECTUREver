package site.metacoding.white.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository // IoC 등록

public class UserRepository {

    // private final Logger log;

    // DI
    private final EntityManager em;

    public User save(User user) {
        // Persistence Context에 영속화 시키기 -> 자동 flush (트랜잭션 종료시)
        log.debug("디버그 : " + user.getId()); // @Slf4j 어노테이션으로 사용할 수 있는 로그
        em.persist(user);
        // System.out.println("ccc : " + user.getId()); // 영속화후 (DB와 동기화된다.)
        log.debug("디버그 : " + user.getId()); // @Slf4j 어노테이션으로 사용할 수 있는 로그
        return user;
    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public User findById(Long id) {
        return em.createQuery("select u from User u where u.id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
