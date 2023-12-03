package Secret.Santa.Secret.Santa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;
    @Column(name = "email")
    @NotBlank(message = "Email is required")
    private String email;
    @Column(name = "password")
    @NotBlank(message = "Password is required")
    private String password;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Group> ownedGroups;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
//    @JoinTable(name = "users_in_groups",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
//    private List<Group> groups;
}

