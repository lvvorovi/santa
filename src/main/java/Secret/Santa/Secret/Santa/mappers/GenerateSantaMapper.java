package Secret.Santa.Secret.Santa.mappers;

import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;

public class GenerateSantaMapper {


    public static GenerateSanta toSanta(GenerateSantaDTO generateSantaDTO) {

        GenerateSanta generateSanta = new GenerateSanta();
        generateSanta.setSanta(generateSantaDTO.getSanta());
        generateSanta.setGroup(generateSantaDTO.getGroup());
        generateSanta.setRecipient(generateSantaDTO.getRecipient());

        return generateSanta;
    }

    public static GenerateSantaDTO toSantaDTO(GenerateSanta generateSanta) {

        GenerateSantaDTO generateSantaDTO = new GenerateSantaDTO();

        generateSantaDTO.setSanta(generateSanta.getSanta());
        generateSantaDTO.setGroup(generateSanta.getGroup());
        generateSantaDTO.setRecipient(generateSanta.getRecipient());

        return generateSantaDTO;
    }


}
