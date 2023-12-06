package Secret.Santa.Secret.Santa.models.DTO;

import Secret.Santa.Secret.Santa.models.Group;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class GiftDTO {
    private int giftId;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private String link;
    @Positive
    @NotNull
    private double price;
    //    @NotNull
    private int createdBy;
    @NotNull
    private int groupId;
//    @NotNull
//    private Group group;
}
