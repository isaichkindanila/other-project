package ru.itis.other.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sign_up_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpToken {
    @Id
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        NOT_USED, USED
    }
}
