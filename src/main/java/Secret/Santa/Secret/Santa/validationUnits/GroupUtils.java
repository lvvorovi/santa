package Secret.Santa.Secret.Santa.validationUnits;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupUtils {

    @Autowired
    private IGroupRepo groupRepository;


    public GroupUtils(IGroupRepo groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group getGroupById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new SantaValidationException("Group does not exist", "id",
                        "Group not found", String.valueOf(id)));
    }
}
