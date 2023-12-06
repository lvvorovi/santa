package Secret.Santa.Secret.Santa.models.DTO;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private int groupId;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Event date is required")
    private LocalDate eventDate;
    @NotBlank(message = "Budget is required")
    private double budget;
    private List<User> user;
    private List<Gift> gifts;
    private List<GenerateSanta> generatedSanta;
    private User owner;
}
