package ru.itis.other.project.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
@Data
@ToString(exclude = "passHash")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        NOT_CONFIRMED, OK
    }
}
