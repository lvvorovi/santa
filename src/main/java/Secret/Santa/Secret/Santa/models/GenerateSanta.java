package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generated_santa_table")
public class GenerateSanta {
    //TODO create group_id, santa_id and user_id oneToOne
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generate_santa_id")
    private int generate_santa_id;

}
