package site.metacoding.white.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public Optional<Board> findById(Long id) {

        // 게시글의 댓글까지 보기 위해서
        // try {
        // Optional<Board> boardOP = Optional.of(
        // em.createNativeQuery("select * from board b inner join user u on b.user_id =
        // u.id where b.id = :id",Board.class)
        // .setParameter("id", id)
        // .getSingleResult());
        // return boardOP;
        // } catch (Exception e) {
        // return Optional.empty();
        // }

        // JPQL 문법
        try {
            Optional<Board> boardOP = Optional.of(
                    em.createQuery("select b from Board b where b.id = :id", Board.class)
                            .setParameter("id", id)
                            .getSingleResult());
            return boardOP;
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public List<Board> findAll() {
        // JPQL 문법
        List<Board> boardList = em.createQuery("select b from Board b", Board.class)
                .getResultList();
        return boardList;
    }

    public void deleteById(Long id) {
        em.createQuery("delete from Board b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
