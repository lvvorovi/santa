package Secret.Santa.Secret.Santa.mappers;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public static Group toGroup(GroupDTO groupDTO, Group group) {

        if (group == null){
            group = new Group();
        }
        group.setName(groupDTO.getName());
        group.setEventDate(groupDTO.getEventDate());
        group.setBudget(groupDTO.getBudget());
        return group;
    }

    public static GroupDTO toGroupDTO(Group group) {

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setName(group.getName());
        groupDTO.setEventDate(group.getEventDate());
        groupDTO.setBudget(group.getBudget());

        return groupDTO;
    }
}
