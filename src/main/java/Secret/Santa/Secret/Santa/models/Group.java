package Secret.Santa.Secret.Santa.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String name;
    @Column(name = "Date")
    private LocalDate eventDate;
    @Column(name = "Budget")
    private double budget;

    @ManyToOne// fix to manytomany
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gift> gifts;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GenerateSanta> generatedSanta;

    @ManyToOne// fix to manytomany
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id")
    private User owner;

}
