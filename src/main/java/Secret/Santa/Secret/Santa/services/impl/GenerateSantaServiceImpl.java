package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGenerateSantaRepo;
import Secret.Santa.Secret.Santa.services.IGenerateSantaService;
import Secret.Santa.Secret.Santa.validationUnits.GenerateSantaUtils;
import Secret.Santa.Secret.Santa.validationUnits.GroupUtils;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Secret.Santa.Secret.Santa.mappers.GenerateSantaMapper.toSanta;

@Service
public class GenerateSantaServiceImpl implements IGenerateSantaService {
    private static final Logger logger = LogManager.getLogger(GenerateSantaServiceImpl.class);
    @Autowired
    private final IGenerateSantaRepo generateSantaRepository;

    private final GenerateSantaUtils generateSantaUtils;
    private final GroupUtils groupUtils;
    private final UserUtils userUtils;


    public GenerateSantaServiceImpl(IGenerateSantaRepo generateSantaRepository,
                                    GenerateSantaUtils generateSantaUtils, GroupUtils groupUtils, UserUtils userUtils) {
        this.generateSantaRepository = generateSantaRepository;
        this.generateSantaUtils = generateSantaUtils;
        this.groupUtils = groupUtils;
        this.userUtils = userUtils;
    }

    @Override
    public List<GenerateSanta> getAllGenerateSantaByGroup(Integer groupId) {
        Group group = groupUtils.getGroupById(groupId);
        return generateSantaRepository.findByGroup(group);
    }

    @Override
    public GenerateSanta createGenerateSanta(GenerateSantaDTO generateSantaDTO) {

        return generateSantaRepository.save(toSanta(generateSantaDTO));
    }

    @Override
    public GenerateSanta getGenerateSantaBySantaAndGroup(Integer santaId, Integer groupId) {

        User santa = userUtils.getUserById(santaId);
        Group group = groupUtils.getGroupById(groupId);

        return generateSantaUtils.getBySantaAndGroup(santa, group);
    }

    @Override
    public void deleteGenerateSantaById(Integer id) {
        generateSantaRepository.deleteById(id);
    }

    @Override
    public void deleteGenerateSantaByGroup(Integer groupId) {

        Group group = groupUtils.getGroupById(groupId);
        generateSantaRepository.deleteByGroup(group);
    }

    @Override
    public void deleteGenerateSantaByUser(Integer userId, Integer groupId) {
        Group group = groupUtils.getGroupById(groupId);
        User user = userUtils.getUserById(userId);
        GenerateSanta generateSanta = generateSantaUtils.getBySantaAndGroup(user, group);
        GenerateSanta generateSantaRecipient = generateSantaUtils.getByUserAndGroup(user, group);

        generateSantaRecipient.setRecipient(generateSanta.getRecipient());
        generateSantaRepository.delete(generateSanta);
    }

    @Override
    public void randomSantaGenerator(Integer groupId) {
        Group group = groupUtils.getGroupById(groupId);
        List<User> usersInGroup = userUtils.getUsersInGroup(group);

        List<User> shuffledUsers = new ArrayList<>(usersInGroup);
        Collections.shuffle(shuffledUsers);

        List<User> recipients = new ArrayList<>(shuffledUsers);

        int maxAttempts = 100;
        for (int i = 0; i < shuffledUsers.size(); i++) {
            User santa = shuffledUsers.get(i);
            int attempts = 0;

            do {
                User recipient = recipients.get((i + attempts) % shuffledUsers.size()); // Circular selection of recipients
                attempts++;

                if (attempts > maxAttempts) {
                    logger.error("Exceeded maximum attempts to find a recipient for Santa: " + santa);
                    break;
                }

                if (!santa.equals(recipient) && !generateSantaUtils.alreadyPaired(santa, recipient)) {
                    GenerateSanta santaPair = new GenerateSanta();
                    santaPair.setGroup(group);
                    santaPair.setSanta(santa);
                    santaPair.setRecipient(recipient);

                    generateSantaRepository.save(santaPair);
                    break;
                }
            } while (true);
        }

    }


}