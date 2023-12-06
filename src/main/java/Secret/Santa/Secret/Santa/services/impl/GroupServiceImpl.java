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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {
    private final IGroupRepo groupRepo;
    private final UserUtils userUtils;
    private final GroupMapper groupMapper;

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepo.findAll();

        return groups.stream()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDTO getGroupById(int groupId) {
        Optional<Group> optionalGroup = groupRepo.findById(groupId);

        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            return groupMapper.toGroupDTO(group);
        }

        throw new EntityNotFoundException("Group not found with id " + groupId);
    }

    @Override
    public GroupDTO editByGroupId(GroupDTO groupDTO) {
        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }
        if (groupDTO.getGroupId() == null) {
            throw new IllegalArgumentException("This user does not have ID");
        }
        Optional<Group> existingGroup = groupRepo.findById(groupDTO.getGroupId());
        if (existingGroup.isPresent()) {
            Group group = groupMapper.toGroup(groupDTO);
            Group updatedGroup = groupRepo.save(group);
            return groupMapper.toGroupDTO(updatedGroup);
        }
        throw new EntityNotFoundException("User not found with id " + groupDTO.getGroupId());
    }

    @Override
    public GroupDTO createGroup(GroupDTO groupDTO) {
        if (groupDTO == null) {
            throw new IllegalArgumentException("GroupDTO cannot be null");
        }
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setEventDate(groupDTO.getEventDate());
        group.setBudget(groupDTO.getBudget());
        Group savedGroup = groupRepo.save(group);
        return groupMapper.toGroupDTO(savedGroup);
    }

    //    @Override
//    public List<Group> getAllGroupsForUser(Integer userId) {
//        User user = userUtils.getUserById(userId);
//        return groupRepo.findByUserContaining(user);
//    }
    @Override
    public List<GroupDTO> getAllGroupsForUser(Integer userId) {
        User user = userUtils.getUserById(userId);
        List<Group> groups = groupRepo.findByUserContaining(user);

        // Assuming groupMapper is a mapper class you have
        return groups.stream()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());
    }

    //
//    @Override
//    public List<Group> getAllGroupsForOwner(Integer userId) {
//        User user = userUtils.getUserById(userId);
//        return groupRepo.findByOwner(user);
//    }
    @Override
    public List<GroupDTO> getAllGroupsForOwner(Integer userId) {
        User user = userUtils.getUserById(userId);
        List<Group> groups = groupRepo.findByOwner(user);

        // Assuming groupMapper is a mapper class you have
        return groups.stream()
                .map(groupMapper::toGroupDTO)
                .collect(Collectors.toList());
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
