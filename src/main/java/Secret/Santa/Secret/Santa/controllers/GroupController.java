package Secret.Santa.Secret.Santa.controllers;


import Secret.Santa.Secret.Santa.models.DTO.GroupDTO;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.services.IGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Secret.Santa.Secret.Santa.mappers.GroupMapper.toGroupDTO;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private IGroupService iGroupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = iGroupService.getAllGroups();
        return ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable int groupId) {
        Group group = iGroupService.getGroupById(groupId);
        return ok(group);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        Group group = iGroupService.createGroup(groupDTO);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable int groupId, @Valid @RequestBody GroupDTO groupDTO) {
        Group group = iGroupService.editByGroupId(groupDTO, groupId);
        return ok(group);
    }

    @GetMapping("/user/{userId}/groups")
    public List<Group> getAllGroupsForUser(@PathVariable("userId") Integer userId) {

        return iGroupService.getAllGroupsForUser(userId);
    }

    @GetMapping("/owner/{userId}/groups")
    public List<Group> getAllGroupsForOwner(@PathVariable("userId") Integer userId) {

        return iGroupService.getAllGroupsForOwner(userId);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int groupId) {
        iGroupService.deleteGroupByGroupId(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
