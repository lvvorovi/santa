package Secret.Santa.Secret.Santa.validationUnits;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGenerateSantaRepo;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateSantaUtils {

    //    @Autowired
//    private IGroupRepo groupRepository;
//
//    @Autowired
//    private IUserRepo userRepository;
    @Autowired
    private final IGenerateSantaRepo generateSantaRepo;

    public GenerateSantaUtils(IGenerateSantaRepo generateSantaRepo
                              //,IGroupRepo groupRepository, IUserRepo userRepository
    ) {
//        this.groupRepository = groupRepository;
//        this.userRepository = userRepository;
        this.generateSantaRepo = generateSantaRepo;
    }

    public GenerateSanta getGenerateSantaById(Integer id) {
        return generateSantaRepo.findById(id)
                .orElseThrow(() -> new SantaValidationException("Generate_Santa does not exist", "id",
                        "Generate_Santa not found", String.valueOf(id)));
    }

    public GenerateSanta getBySantaAndGroup(User user, Group group) {
        return generateSantaRepo.findBySantaAndGroup(user, group)
                .orElseThrow(() -> new SantaValidationException("Generate_Santa does not exist", "id",
                        "Generate_Santa not found", String.valueOf(user.getUser_id())));
    }

    public GenerateSanta getByUserAndGroup(User user, Group group) {
        return generateSantaRepo.findByRecipientAndGroup(user, group)
                .orElseThrow(() -> new SantaValidationException("Generate_Santa does not exist", "id",
                        "Generate_Santa not found", String.valueOf(user.getUser_id())));
    }

    public boolean alreadyPaired(User user1, User user2) {
        return generateSantaRepo.existsBySantaAndRecipient(user1, user2) ||
                generateSantaRepo.existsBySantaAndRecipient(user2, user1);
    }

}
