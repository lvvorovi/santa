package Secret.Santa.Secret.Santa.controllers;


import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.services.IGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Secret.Santa.Secret.Santa.mappers.GroupMapper.toGroupDTO;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private IGroupService iGroupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        try {
            List<GroupDTO> groupsDTOs = iGroupService.getAllGroups();
            return new ResponseEntity<>(groupsDTOs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get all groups", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroupById(@Valid
                                                     @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                                     @PathVariable int groupId) {
        try {
            GroupDTO groupDTO = iGroupService.getGroupById(groupId);
            return ResponseEntity.ok(groupDTO);
        } catch (Exception e) {
            logger.error("Failed to get group with ID: {}", groupId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO createdGroupDTO = iGroupService.createGroup(groupDTO);
            return new ResponseEntity<>(createdGroupDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create group", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO editedGroupDTO = iGroupService.editByGroupId(groupDTO);
            return ResponseEntity.ok(editedGroupDTO);
        } catch (Exception e) {
            logger.error("Failed to update group with ID: {}", groupId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/groups")
    public List<GroupDTO> getAllGroupsForUser(@PathVariable("userId") Integer userId) {
        try {
            return iGroupService.getAllGroupsForUser(userId);
        } catch (Exception e) {
            logger.error("Error retrieving groups for user with ID: {}", userId, e);
            throw e;
        }
    }

    @GetMapping("/owner/{userId}/groups")
    public List<GroupDTO> getAllGroupsForOwner(@PathVariable("userId") Integer userId) {
        try {
            return iGroupService.getAllGroupsForOwner(userId);
        } catch (Exception e) {
            logger.error("Error retrieving groups for owner with ID: {}", userId, e);
            throw e;
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int groupId) {
        try {
            boolean deleted = iGroupService.deleteGroupByGroupId(groupId);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Failed to delete group with ID: {}", groupId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{groupId}/users/{userId}/newUsers")
    public ResponseEntity<GroupDTO> addUserToGroup(@PathVariable int groupId, @Valid @PathVariable int userId) {

        var updatedGroup = iGroupService.addUserToGroup(groupId, userId );

        return ok(toGroupDTO(updatedGroup));
    }

    @GetMapping(value = "/{groupId}/users")
    @ResponseBody
    public List<User> getAllUsersById(@PathVariable int groupId) {
        return iGroupService.getAllUsersById(groupId);
    }

}
