package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupServiceImpl implements IGroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    IGroupRepo groupRepo;

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
    public Group editByGroupId(GroupDTO groupDTO, int groupId) {
        try {
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
            throw new RuntimeException("Group not found with id: " + groupId);
        } catch (RuntimeException e) {
            logger.error("Error occurred while updating group with ID: {}", groupId, e);
            throw e;
        }
    }
    @Override
    public Group createGroup(GroupDTO groupDTO) {
        try {
            Group group = new Group();
            group.setName(groupDTO.getName());
            group.setEventDate(groupDTO.getEventDate());
            group.setBudget(groupDTO.getBudget());
            return groupRepo.save(group);
        } catch (Exception e) {
            logger.error("Failed to create group", e);
            throw e;
        }
    }
    public boolean deleteGroupByGroupId(int groupId) {
        try {
            if (!groupRepo.existsById(groupId)) {
                throw new RuntimeException("Group not found with id: " + groupId);
            }
            groupRepo.deleteById(groupId);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete group with ID: {}", groupId, e);
            throw e;
        }
    }


}
