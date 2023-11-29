package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {

}
