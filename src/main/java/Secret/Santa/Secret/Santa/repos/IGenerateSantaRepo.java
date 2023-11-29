package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IGenerateSantaRepo extends JpaRepository<GenerateSanta, Integer> {
    List<GenerateSanta> findAll();

    List<GenerateSanta> findBySanta(User santa);

    GenerateSanta findBySantaAndGroup(User santa, Group group);

    //List<GenerateSanta> findAllByGroup(Group group);

    List<GenerateSanta> findByGroup(Group group);

    @Transactional
    void deleteByGroup(Group group);
}
