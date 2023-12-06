package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;

import java.util.ArrayList;
import java.util.List;

public interface IGroupService {
    List<GroupDTO> getAllGroups();

    GroupDTO editByGroupId(GroupDTO groupDTO);

    GroupDTO createGroup(GroupDTO groupDTO);

    List<GroupDTO> getAllGroupsForUser(Integer userId);

    List<GroupDTO> getAllGroupsForOwner(Integer userId);

    boolean deleteGroupByGroupId(int groupId);

    GroupDTO getGroupById(int groupId);

    GroupDTO addUserToGroup(int groupId, int userId);

    List<User> getAllUsersById(int groupId);
}
