package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.GenerateSantaDTO;
import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGenerateSantaRepo;
import Secret.Santa.Secret.Santa.services.IGenerateSantaService;
import Secret.Santa.Secret.Santa.validationUnits.GenerateSantaUtils;
import Secret.Santa.Secret.Santa.validationUnits.GroupUtils;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Secret.Santa.Secret.Santa.mappers.GenerateSantaMapper.toSanta;

@Service
public class GenerateSantaServiceImpl implements IGenerateSantaService {
    private static final Logger logger = LoggerFactory.getLogger(GenerateSantaServiceImpl.class);

    @Autowired
    private IGenerateSantaRepo generateSantaRepository;

    private GenerateSantaUtils generateSantaUtils;
    private GroupUtils groupUtils;
    private UserUtils userUtils;


    public GenerateSantaServiceImpl(IGenerateSantaRepo generateSantaRepository,
                                    GenerateSantaUtils generateSantaUtils, GroupUtils groupUtils, UserUtils userUtils) {
        this.generateSantaRepository = generateSantaRepository;
        this.generateSantaUtils = generateSantaUtils;
        this.groupUtils = groupUtils;
        this.userUtils = userUtils;
    }

    @Override
    public List<GenerateSanta> getAllGenerateSantaByGroup(Integer groupId) {
        try {
            Group group = groupUtils.getGroupById(groupId);//groupRepository.findById(groupId).orElseThrow();
            return generateSantaRepository.findByGroup(group);
        } catch (Exception e) {
            logger.error("Error retrieving all GenerateSanta by group ID: {}", groupId, e);
            throw e;
        }
    }

    @Override
    public GenerateSanta createGenerateSanta(GenerateSantaDTO generateSantaDTO) {
        try {
            return generateSantaRepository.save(toSanta(generateSantaDTO));
        } catch (Exception e) {
            logger.error("Error creating GenerateSanta", e);
            throw e;
        }
    }

    @Override
    public GenerateSanta getGenerateSantaBySantaAndGroup(Integer santaId, Integer groupId) {
        try {
            User santa = userUtils.getUserById(santaId);
            Group group = groupUtils.getGroupById(groupId);
            return generateSantaUtils.getBySantaAndGroup(santa, group);
        } catch (Exception e) {
            logger.error("Error retrieving GenerateSanta by santa ID: {} and group ID: {}", santaId, groupId, e);
            throw e;
        }
    }

    @Override
    public void deleteGenerateSantaById(Integer id) {
        try {
            generateSantaRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting GenerateSanta by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteGenerateSantaByGroup(Integer groupId) {
        try {
            Group group = groupUtils.getGroupById(groupId);
            generateSantaRepository.deleteByGroup(group);
        } catch (Exception e) {
            logger.error("Error deleting GenerateSanta by group ID: {}", groupId, e);
            throw e;
        }
    }

    @Override
    public void deleteGenerateSantaByUser(Integer userId, Integer groupId) {
        try {
            Group group = groupUtils.getGroupById(groupId);
            User user = userUtils.getUserById(userId);
            GenerateSanta generateSanta = generateSantaUtils.getBySantaAndGroup(user, group);
            GenerateSanta generateSantaRecipient = generateSantaUtils.getByUserAndGroup(user, group);
            generateSantaRecipient.setRecipient(generateSanta.getRecipient());
            generateSantaRepository.delete(generateSanta);
        } catch (Exception e) {
            logger.error("Error deleting GenerateSanta by user ID: {} and group ID: {}", userId, groupId, e);
            throw e;
        }
    }
//@Override
//        public void randomSantaGenerator(Group group) {
//            // Retrieve users in the given group
//            List<User> usersInGroup = userRepository.findByGroup(group);
//
//            // Shuffle the users
//            List<User> shuffledUsers = new ArrayList<>(usersInGroup);
//            Collections.shuffle(shuffledUsers);
//
//            // Pair users and save into GenerateSanta entity
//            for (int i = 0; i < shuffledUsers.size(); i++) {
//                User santa = shuffledUsers.get(i);
//                User recipient = shuffledUsers.get((i + 1) % shuffledUsers.size());
//
//                // Create GenerateSanta entity for each pair
//                GenerateSanta santaPair = new GenerateSanta();
//                santaPair.setGroup(group);
//                santaPair.setSanta(santa);
//                santaPair.setRecipient(recipient);
//
//                // Save the pair into the database
//                generateSantaRepository.save(santaPair);
//            }
//        }

}