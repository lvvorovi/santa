package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.impl.GenerateSantaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/generate-santa")
public class GenerateSantaController {

    @Autowired
    private GenerateSantaServiceImpl generateSantaService;
    @Autowired
    private IUserRepo userRepo;
    @GetMapping("/all")
    public List<GenerateSanta> getAllGenerateSanta() {
        return generateSantaService.getAllGenerateSanta();
    }

    @GetMapping("/bysanta/{santaId}")
    public List<GenerateSanta> getGenerateSantaBySanta(@PathVariable("santaId") Integer santaId) {

        User santa = userRepo.getById(santaId); //fix
        return generateSantaService.getGenerateSantaBySanta(santa);
    }

}
