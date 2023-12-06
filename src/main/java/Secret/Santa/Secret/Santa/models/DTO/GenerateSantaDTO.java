package Secret.Santa.Secret.Santa.models.DTO;

import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateSantaDTO {
    private Integer generateSantaId;

    private Group group;

    private User santa;

    private User recipient;
}
