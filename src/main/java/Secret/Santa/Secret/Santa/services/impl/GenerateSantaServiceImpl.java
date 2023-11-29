package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.GenerateSanta;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGenerateSantaRepo;
import Secret.Santa.Secret.Santa.repos.IGroupRepo;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GenerateSantaServiceImpl {

    @Autowired
    private IUserRepo userRepository;
    @Autowired
    private IGroupRepo groupRepository;

    @Autowired
    private IGenerateSantaRepo generateSantaRepository;

    public List<GenerateSanta> getAllGenerateSanta() {
        return generateSantaRepository.findAll();
    }

    public GenerateSanta getGenerateSantaBySantaAndGroup(Integer santaId, Integer groupId) {

        //  Optional<User> santa = userRepository.findById(santaId);
        User santa = userRepository.findById(santaId).orElseThrow();//() -> new YourCustomException("Group not found"));
        //  Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = groupRepository.findById(groupId).orElseThrow();//() -> new YourCustomException("Group not found"));

        return generateSantaRepository.findBySantaAndGroup(santa, group);
    }

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
