package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int user_id;
    private String name;
    private String email;
    private String password;
}
