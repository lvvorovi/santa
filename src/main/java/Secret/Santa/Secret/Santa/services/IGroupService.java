package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;

import java.util.List;

public interface IGroupService {
    List<Group> getAllGroups();

    Group editByGroupId(GroupDTO groupDTO, int groupId);

    Group createGroup(GroupDTO groupDTO);

    List<Group> getAllGroupsForUser(Integer userId);

    List<Group> getAllGroupsForOwner(Integer userId);

    boolean deleteGroupByGroupId(int groupId);

    Group getGroupById(int groupId);

    Group addUserToGroup(int groupId, int userId);

    List<User> getAllUsersById(int groupId);
}
