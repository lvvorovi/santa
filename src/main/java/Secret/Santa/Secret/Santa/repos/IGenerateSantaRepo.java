package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGenerateSantaRepo extends JpaRepository<GenerateSanta, Integer> {
    List<GenerateSanta> findAll();

    List<GenerateSanta> findBySanta(User santa);

    Optional<GenerateSanta> findBySantaAndGroup(User santa, Group group);

    List<GenerateSanta> findByGroup(Group group);

    @Transactional
    void deleteByGroup(Group group);

    Optional<GenerateSanta> findByRecipientAndGroup(User user, Group group);

    boolean existsBySantaAndRecipient(User user1, User user2);
}
