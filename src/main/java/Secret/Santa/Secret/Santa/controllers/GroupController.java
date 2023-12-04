package Secret.Santa.Secret.Santa.controllers;


import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.services.IGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private IGroupService iGroupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        try {
            List<Group> groups = iGroupService.getAllGroups();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get all groups", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable int groupId) {
        try {
            Group group = iGroupService.getGroupById(groupId);
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            logger.error("Failed to get group with ID: {}", groupId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        try {
            Group group = iGroupService.createGroup(groupDTO);
            return new ResponseEntity<>(group, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create group", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable int groupId, @Valid @RequestBody GroupDTO groupDTO) {
        try {
            Group group = iGroupService.editByGroupId(groupDTO, groupId);
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            logger.error("Failed to update group with ID: {}", groupId, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
