package ru.itis.other.project.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@Data
@ToString(exclude = "passHash")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "pass_hash")
    private String passHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        NOT_CONFIRMED, OK
    }
}
