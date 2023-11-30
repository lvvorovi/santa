package Secret.Santa.Secret.Santa.models.DTO;

import Secret.Santa.Secret.Santa.models.Group;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private String link;
    private double price;
    private int created_by;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
