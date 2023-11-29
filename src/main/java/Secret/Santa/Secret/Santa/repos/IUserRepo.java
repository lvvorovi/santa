package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepo extends JpaRepository<User, Integer> {

}
