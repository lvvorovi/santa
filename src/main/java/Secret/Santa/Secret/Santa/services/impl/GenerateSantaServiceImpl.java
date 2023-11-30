package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGenerateSantaRepo;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IGenerateSantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateSantaServiceImpl implements IGenerateSantaService {

    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private IGenerateSantaRepo generateSantaRepository;

    @Autowired
    private IGroupRepo groupRepository;


    public GenerateSantaServiceImpl(IGenerateSantaRepo generateSantaRepository,
                                    IUserRepo userRepository) {
        this.generateSantaRepository = generateSantaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GenerateSanta> getAllGenerateSantaByGroup(Integer groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        return generateSantaRepository.findByGroup(group);
    }

    @Override
    public GenerateSanta createGenerateSanta(GenerateSanta generateSanta) {
        return generateSantaRepository.save(generateSanta);
    }

    @Override
    public GenerateSanta getGenerateSantaBySantaAndGroup(Integer santaId, Integer groupId) {

        User santa = userRepository.findById(santaId).orElseThrow();//() -> new YourCustomException("Group not found"));
        Group group = groupRepository.findById(groupId).orElseThrow();//() -> new YourCustomException("Group not found"));

        return generateSantaRepository.findBySantaAndGroup(santa, group);
    }

    @Override
    public void deleteGenerateSantaById(Integer id) {
        generateSantaRepository.deleteById(id);
    }

    @Override
    public void deleteGenerateSantaByGroup(Integer groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow();
        generateSantaRepository.deleteByGroup(group);
    }
//@Override
//        public void shuffleAndSaveSecretSantaPairs(Group group) {
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