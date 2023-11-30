package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGiftRepo extends JpaRepository<Gift, Integer> {
}

