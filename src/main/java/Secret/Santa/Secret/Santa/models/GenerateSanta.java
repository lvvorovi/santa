package Secret.Santa.Secret.Santa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generate_santa_id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn(name = "santa_id")
    private User santa;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;


}
