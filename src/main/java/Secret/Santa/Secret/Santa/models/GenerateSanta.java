package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generated_santa_table")
public class GenerateSanta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "generate_santa_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
