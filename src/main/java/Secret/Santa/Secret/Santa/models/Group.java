package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "group_table")
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private int group_id;
    private String name;
    //TODO date
    private double budget;

    //TODO user_id with manyToOne
}
