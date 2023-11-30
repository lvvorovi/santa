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
    private int giftId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Link")
    private String link;
    @Column(name = "Price")
    private double price;
    @Column(name = "Created_by")
    private int created_by;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "group_id")
    private Group group;

}