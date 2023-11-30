package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IGenerateSantaService;
import Secret.Santa.Secret.Santa.services.IUserService;
import Secret.Santa.Secret.Santa.services.impl.GenerateSantaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generate-santa")
public class GenerateSantaController {

    @Autowired
    private IGenerateSantaService generateSantaService;
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IGroupRepo groupRepo;

    @PostMapping("/create")
    public ResponseEntity<String> createGenerateSanta(@RequestBody GenerateSantaDTO generateSantaDTO) {

        GenerateSanta createdSanta = generateSantaService.createGenerateSanta(generateSantaDTO);
        if (createdSanta != null) {
            return ResponseEntity.ok("GenerateSanta created successfully with ID: " + createdSanta.getId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create GenerateSanta");
        }
    }

    @GetMapping("/all/{groupId}")
    public List<GenerateSanta> getAllGenerateSantaByGroup(@PathVariable("groupId") Integer groupId) {

        return generateSantaService.getAllGenerateSantaByGroup(groupId);
    }

    @GetMapping("/bysanta/{santaId}")
    public GenerateSanta getGenerateSantaBySantaAndGroup(@PathVariable("santaId") Integer santaId,
                                                         @RequestParam Integer groupId) {
        return generateSantaService.getGenerateSantaBySantaAndGroup(santaId, groupId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenerateSantaBySantaId(@PathVariable("id") Integer id) {
        generateSantaService.deleteGenerateSantaById(id);
        return ResponseEntity.ok("GenerateSanta with ID " + id + " deleted successfully");
    }

    @DeleteMapping("/deletebygroup/{groupId}")
    public void deleteGenerateSantaByGroup(@PathVariable("groupId") Integer groupId) {

        generateSantaService.deleteGenerateSantaByGroup(groupId);
        //ResponseEntity.ok("GenerateSanta entries for Group ID " + groupId + " deleted successfully");
    }

    @DeleteMapping("/deletebyuser/{userId}")
    public void deleteGenerateSantaByUser(@PathVariable("userId") Integer userId,
                                          @RequestParam Integer groupId) {

        generateSantaService.deleteGenerateSantaByUser(userId, groupId);
        //ResponseEntity.ok("GenerateSanta entries for Group ID " + groupId + " deleted successfully");
    }


}
