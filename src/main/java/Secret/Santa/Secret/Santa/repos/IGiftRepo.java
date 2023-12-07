package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGiftRepo extends JpaRepository<Gift, Integer> {

    List<Gift> findByCreatedBy(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Gift g WHERE g.createdBy = :user")
    void deleteByCreatedBy(@Param("user") User user);
}

