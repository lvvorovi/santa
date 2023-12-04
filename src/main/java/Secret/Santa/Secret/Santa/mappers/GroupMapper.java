package Secret.Santa.Secret.Santa.mappers;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;

public class GroupMapper {
    public static Group toGroup(GroupDTO groupDTO) {

        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setEventDate(groupDTO.getEventDate());
        group.setBudget(groupDTO.getBudget());
        group.setUser(groupDTO.getUser());
        group.setGifts(groupDTO.getGifts());
        group.setGeneratedSanta(groupDTO.getGeneratedSanta());
        group.setOwner(groupDTO.getOwner());

        return group;
    }

    public static GroupDTO toGroupDTO(Group group) {

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setName(group.getName());
        groupDTO.setEventDate(group.getEventDate());
        groupDTO.setBudget(group.getBudget());
        groupDTO.setUser(group.getUser());
        groupDTO.setGifts(group.getGifts());
        groupDTO.setGeneratedSanta(group.getGeneratedSanta());
        groupDTO.setOwner(group.getOwner());

        return groupDTO;
    }


}
