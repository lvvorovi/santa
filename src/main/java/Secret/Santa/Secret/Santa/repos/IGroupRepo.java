package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepo extends JpaRepository<Group, Integer> {
}
