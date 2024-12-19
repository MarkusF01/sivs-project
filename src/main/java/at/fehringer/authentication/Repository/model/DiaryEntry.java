package at.fehringer.authentication.Repository.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diaryentries")
@Getter @Setter
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diaryentries_id_seq")
    @SequenceGenerator(name = "diaryentries_id_seq", sequenceName = "diaryentries_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "entry_title", nullable = false)
    private String entryTitle;

    @Column(name = "entry_content", nullable = false)
    private String entryContent;

    @Column(name = "entry_date", nullable = false)
    private String entryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}