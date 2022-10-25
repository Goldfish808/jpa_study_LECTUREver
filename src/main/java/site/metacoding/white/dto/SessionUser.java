package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

@Setter
@Getter
public class SessionUser {
    private Long id;
    private String username;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public User toEntity() {
        return User.builder().id(id).username(username).build();
    }
}
