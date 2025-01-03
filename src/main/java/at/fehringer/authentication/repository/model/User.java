package at.fehringer.authentication.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "secret_question", nullable = false)
    private String secretQuestion;

    @Column(name = "secret_answer", nullable = false)
    private String secretAnswer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DiaryEntry> diaryEntries;
}
