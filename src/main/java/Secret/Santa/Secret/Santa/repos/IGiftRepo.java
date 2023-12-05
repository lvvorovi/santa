package Secret.Santa.Secret.Santa.repos;

import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGiftRepo extends JpaRepository<Gift, Integer> {

    //    @Query("SELECT g FROM Gift g WHERE g.created_by = :userId")
//    List<Gift> findByCreatedBy(@Param("userId") int userId);
    List<Gift> findByCreatedBy(User user);


}

