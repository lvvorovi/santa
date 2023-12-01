package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToOne
    @JoinColumn(name = "santa_id")
    private User santa;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;


}
