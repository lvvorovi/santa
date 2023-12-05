package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.mappers.GroupMapper;
import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.services.IGroupService;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {
    private final IGroupRepo groupRepo;
    private final UserUtils userUtils;
    private final GroupMapper groupMapper;

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
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setEventDate(groupDTO.getEventDate());
        group.setBudget(groupDTO.getBudget());
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
        Optional<Group> optionalGroup = groupRepo.findById(groupId);
        if (optionalGroup.isPresent()) {
            try {
                groupRepo.deleteById(groupId);
            } catch (Exception exception) {
                return false;
            }
            return true;
        }
        throw new EntityNotFoundException("Group not found with id " + groupId);
    }


}
