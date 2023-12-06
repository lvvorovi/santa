package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.mappers.GroupMapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    private final IGroupRepo groupRepo;

    @Autowired
    private final IUserRepo userRepo;

    private final UserUtils userUtils;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(IGroupRepo groupRepo, IUserRepo userRepo, UserUtils userUtils, GroupMapper groupMapper) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
        this.userUtils = userUtils;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<Group> getAllGroups() {
        try {
            return groupRepo.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve all groups", e);
            throw e;
        }
    }

    @Override
    public Group getGroupById(int groupId) {
        try {
            return groupRepo.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
        } catch (Exception e) {
            logger.error("Failed to retrieve group with ID: {}", groupId, e);
            throw e;
        }
    }

    @Override
    public GroupDTO editByGroupId(GroupDTO groupDTO, int groupId) {
//        Optional<Group> optionalGroup = groupRepo.findById(groupId);
//        if (optionalGroup.isPresent()) {
//            Group group = optionalGroup.get();
//            if (Objects.nonNull(groupDTO.getName())) {
//                group.setName(groupDTO.getName());
//            }
//            if (Objects.nonNull(groupDTO.getEventDate())) {
//                group.setEventDate(groupDTO.getEventDate());
//            }
//            group.setBudget(groupDTO.getBudget());
//            return groupRepo.save(group);
//        }
//        throw new EntityNotFoundException(" not found with id " + groupId);

        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }
        Optional<Group> existingGroup = groupRepo.findById(groupId);
        if (existingGroup.isPresent()) {
            Group group = existingGroup.get();
            group = groupMapper.toGroup(groupDTO, group);
            groupRepo.save(group);
            return groupMapper.toGroupDTO(group);
        }
        throw new EntityNotFoundException("User not found with id " + groupId);
    }

    @Override
    public Group createGroup(GroupDTO groupDTO) {

        try {
            Group group = new Group();
            group = groupMapper.toGroup(groupDTO);

            return groupRepo.save(group);
        } catch (Exception e) {
            logger.error("Failed to create group", e);
            throw e;
        }
    }

    @Override
    public List<Group> getAllGroupsForUser(Integer userId) {
        try {
            User user = userUtils.getUserById(userId);
            return groupRepo.findByUserContaining(user);
        } catch (Exception e) {
            logger.error("Error retrieving all groups for user with ID: {}", userId, e);
            throw e;
        }
    }

    @Override
    public List<Group> getAllGroupsForOwner(Integer userId) {
        try {
            User user = userUtils.getUserById(userId);
            return groupRepo.findByOwner(user);
        } catch (Exception e) {
            logger.error("Error retrieving all groups for owner with ID: {}", userId, e);
            throw e;
        }
    }

    @Override
    public boolean deleteGroupByGroupId(int groupId) {
        Optional<Group> optionalGroup = groupRepo.findById(groupId);
        if (optionalGroup.isPresent()) {
            try {
                groupRepo.deleteById(groupId);
                return true;
            } catch (Exception e) {
                logger.error("Error deleting group with ID: {}", groupId, e);
                return false;
            }
        } else {
            logger.error("Attempted to delete a group that does not exist with ID: {}", groupId);
            throw new EntityNotFoundException("Group not found with id " + groupId);
        }
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
