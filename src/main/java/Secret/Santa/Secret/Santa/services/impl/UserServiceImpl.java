package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepo iUserRepo;

    @Override
    public List<User> getAllUsers() {
        return iUserRepo.findAll();
    }

    @Override
    public User findByUserid(int userid) {
        Optional<User> optionalLessor = iUserRepo.findById(userid);

        return optionalLessor.orElseThrow(() -> new EntityNotFoundException("User not found with id " + userid));
    }

    @Override
    public User editByUserId(UserDTO userDTO, int userid) {
        Optional<User> optionalUser = iUserRepo.findById(userid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (Objects.nonNull(userDTO.getName())) {
                user.setName(userDTO.getName());
            }
            if (Objects.nonNull(userDTO.getEmail())) {
                user.setEmail(userDTO.getEmail());
            }
            if (Objects.nonNull(userDTO.getPassword())) {
                user.setPassword(userDTO.getPassword());
            }

            return iUserRepo.save(user);
        }
        throw new EntityNotFoundException(" not found with id " + userid);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
//        user.setGroups(new ArrayList<>());
        return iUserRepo.save(user);
    }

    @Override
    public boolean deleteUserByUserid(int userid) {
        return false;
    }


}
