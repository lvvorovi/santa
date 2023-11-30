package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IGenerateSantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generate_santa")
public class GenerateSantaController {

    @Autowired
    private final IGenerateSantaService generateSantaService;

    public GenerateSantaController(IGenerateSantaService generateSantaService) {
        this.generateSantaService = generateSantaService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGenerateSanta(@RequestBody GenerateSantaDTO generateSantaDTO) {

        GenerateSanta createdSanta = generateSantaService.createGenerateSanta(generateSantaDTO);
        if (createdSanta != null) {
            return ResponseEntity.ok("GenerateSanta created successfully with ID: " + createdSanta.getId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create GenerateSanta");
        }
    }

    @GetMapping("/all_in_group/{groupId}")
    public List<GenerateSanta> getAllGenerateSantaByGroup(@PathVariable("groupId") Integer groupId) {

        return generateSantaService.getAllGenerateSantaByGroup(groupId);
    }

    @GetMapping("/santa_group/{santaId}")
    public GenerateSanta getGenerateSantaBySantaAndGroup(@PathVariable("santaId") Integer santaId,
                                                         @RequestParam Integer groupId) {
        return generateSantaService.getGenerateSantaBySantaAndGroup(santaId, groupId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenerateSantaBySantaId(@PathVariable("id") Integer id) {
        generateSantaService.deleteGenerateSantaById(id);
        return ResponseEntity.ok("GenerateSanta with ID " + id + " deleted successfully");
    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<String> deleteGenerateSantaByGroup(@PathVariable("groupId") Integer groupId) {

        generateSantaService.deleteGenerateSantaByGroup(groupId);
        return ResponseEntity.ok("GenerateSanta entries for Group ID " + groupId + " deleted successfully");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteGenerateSantaByUser(@PathVariable("userId") Integer userId,
                                                            @RequestParam Integer groupId) {

        generateSantaService.deleteGenerateSantaByUser(userId, groupId);
        return ResponseEntity.ok("GenerateSanta entries for Group ID " + groupId + " deleted successfully");
    }

    @PostMapping("/random/{groupId}")
    public ResponseEntity<String> generateRandomSanta(@PathVariable("groupId") Integer groupId) {

        generateSantaService.randomSantaGenerator(groupId);
        return ResponseEntity.ok("Random Santa pairs generated successfully for Group ID: " + groupId);

    }

}
