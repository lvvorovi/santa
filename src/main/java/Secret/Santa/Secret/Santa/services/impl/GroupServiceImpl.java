package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.services.IGroupService;
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
        throw new EntityNotFoundException(" not found with id "+ groupId);
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
    public boolean deleteGroupByGroupId(int groupId) {
        if (groupRepo.existsById(groupId)) {
            groupRepo.deleteById(groupId);
            return true;
        }
        return false;
    }


}
