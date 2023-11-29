package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private int giftId;
    private String name;
    private String description;
    private String link;
    private double price;
    private int created_by;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "group_id")
    private Group group;

}
