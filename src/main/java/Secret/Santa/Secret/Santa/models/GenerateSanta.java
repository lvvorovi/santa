package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generated_santa_table")
public class GenerateSanta {
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
