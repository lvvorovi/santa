package Secret.Santa.Secret.Santa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;


    @OneToMany(mappedBy = "user")
    private ArrayList<Group> groups;
}
