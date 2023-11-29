package Secret.Santa.Secret.Santa.models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @Email
    private String email;
    private String password;
}
