package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;

import java.util.List;

public interface IGenerateSantaService {
    GenerateSanta createGenerateSanta(GenerateSantaDTO generateSantaDTO);

    List<GenerateSanta> getAllGenerateSantaByGroup(Integer groupId);

    GenerateSanta getGenerateSantaBySantaAndGroup(Integer santaId, Integer groupId);

    void deleteGenerateSantaById(Integer id);

    void deleteGenerateSantaByGroup(Integer groupId);

//    void shuffleAndSaveSecretSantaPairs(Group group);
}
