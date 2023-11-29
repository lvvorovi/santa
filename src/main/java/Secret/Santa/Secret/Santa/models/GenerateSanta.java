package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generated_santa_table")
public class GenerateSanta {
    //TODO create group_id, santa_id and user_id oneToOne

}
