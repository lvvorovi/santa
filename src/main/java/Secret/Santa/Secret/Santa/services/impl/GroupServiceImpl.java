package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IGroupService;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    IGroupRepo groupRepo;

    @Autowired
    IUserRepo userRepo;

    private final UserUtils userUtils;

    public GroupServiceImpl(UserUtils userUtils) {
        this.userUtils = userUtils;
    }


    @Override
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    @Override
    public Group getGroupById(int groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
    }

    @Override
    public Group editByGroupId(GroupDTO groupDTO, int groupId) {
        Optional<Group> optionalGroup = groupRepo.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (Objects.nonNull(groupDTO.getName())) {
                group.setName(groupDTO.getName());
            }
            if (Objects.nonNull(groupDTO.getEventDate())) {
                group.setEventDate(groupDTO.getEventDate());
            }
            group.setBudget(groupDTO.getBudget());
            return groupRepo.save(group);
        }
        throw new EntityNotFoundException(" not found with id " + groupId);
    }

    @Override
    public Group createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setEventDate(groupDTO.getEventDate());
        group.setBudget(groupDTO.getBudget());
        group.setUser(groupDTO.getUser());
        return groupRepo.save(group);
    }

    @Override
    public List<Group> getAllGroupsForUser(Integer userId) {
        User user = userUtils.getUserById(userId);
        return groupRepo.findByUserContaining(user);
    }

    @Override
    public List<Group> getAllGroupsForOwner(Integer userId) {
        User user = userUtils.getUserById(userId);
        return groupRepo.findByOwner(user);
    }

    @Override
    public boolean deleteGroupByGroupId(int groupId) {
        if (groupRepo.existsById(groupId)) {
            groupRepo.deleteById(groupId);
            return true;
        }
        return false;
    }

    public Group addUserToGroup(int groupId, int userId) {
        var existingGroup = groupRepo.findById(groupId)
                .orElseThrow(() -> new SantaValidationException("Group does not exist",
                        "id", "Group not found", String.valueOf(groupId)));

        if (existingGroup.getUser().stream().anyMatch(user -> user.getUserId() == userId)) {
            throw new IllegalArgumentException("User is already in the group");
        }

        var existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new SantaValidationException("User does not exist",
                        "id", "User not found", String.valueOf(userId)));

        List<User> existingUserList = existingGroup.getUser();
        existingUserList.add(existingUser);
        existingGroup.setUser(existingUserList);

        return groupRepo.save(existingGroup);
    }


    public List<User> getAllUsersById(int groupId) {
        return groupRepo.findById(groupId).get().getUser();

    }

}
