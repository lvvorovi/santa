package Secret.Santa.Secret.Santa.controllers;


import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.services.IGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private IGroupService iGroupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groupDTOs = iGroupService.getAllGroups();
        return ResponseEntity.ok(groupDTOs);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroupById(@Valid
                                                 @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                                 @PathVariable int groupId) {
        GroupDTO groupDTO = iGroupService.getGroupById(groupId);
        return ResponseEntity.ok(groupDTO);
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        GroupDTO createdGroupDTO = iGroupService.createGroup(groupDTO);
        return ResponseEntity.ok(createdGroupDTO);
    }

    @PutMapping
    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        GroupDTO group = iGroupService.editByGroupId(groupDTO);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/user/{userId}/groups")
    public List<GroupDTO> getAllGroupsForUser(@PathVariable("userId") Integer userId) {

        return iGroupService.getAllGroupsForUser(userId);
    }

    @GetMapping("/owner/{userId}/groups")
    public List<GroupDTO> getAllGroupsForOwner(@PathVariable("userId") Integer userId) {

        return iGroupService.getAllGroupsForOwner(userId);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int groupId) {
        iGroupService.deleteGroupByGroupId(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
