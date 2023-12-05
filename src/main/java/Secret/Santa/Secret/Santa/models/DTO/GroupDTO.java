package Secret.Santa.Secret.Santa.models.DTO;

import Secret.Santa.Secret.Santa.models.User;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private int groupId;
    private String name;
    private LocalDate eventDate;
    private double budget;
}
