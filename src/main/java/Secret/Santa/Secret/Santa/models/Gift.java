package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "gift_table")
@AllArgsConstructor
@NoArgsConstructor
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_id")
    private int gift_id;
    private String name;
    private String description;
    private String link;
    private double price;
    private int created_by;

    //TODO oneToMany group_id

}
