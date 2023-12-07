package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepo extends JpaRepository<Group, Integer> {
    List<Group> findByUserContaining(User user);

    List<Group> findByOwner(User user);

    @Transactional
    void deleteByOwner(User user);
}
