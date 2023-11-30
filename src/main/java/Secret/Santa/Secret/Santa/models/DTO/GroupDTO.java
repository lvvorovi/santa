package Secret.Santa.Secret.Santa.models.DTO;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class GroupDTO {
    private String name;
    private LocalDate eventDate;
    private double budget;
}
