package Secret.Santa.Secret.Santa.models;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "group_table")
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private int groupId;
    @Column(name = "Name")
    @NotNull
    private String name;
    @Column(name = "Date")
    private LocalDate eventDate;
    @Column(name = "Budget")
    private double budget;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "group")
    private ArrayList<Gift> gifts;

    @OneToMany(mappedBy = "group")
    private ArrayList<GenerateSanta> generatedSanta;


}
