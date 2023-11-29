package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGenerateSantaRepo extends JpaRepository<GenerateSanta, Integer> {
    List<GenerateSanta> findAll();

    List<GenerateSanta> findBySanta(User santa);
}
