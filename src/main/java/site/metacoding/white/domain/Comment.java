package site.metacoding.white.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String content;

    @Builder
    public Comment(Long id, String content, User user, Board board) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.board = board;
    }

    // 누가 썼는지 봐야함,, User
    // 일반적으로는 private Long userId;
    // Hibernate 에서는 아래와 같이 할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 그리고 어느 글에 댓글을 작성했는지 알아야함,, Board
    // 일반적으로는 private Long boardId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
