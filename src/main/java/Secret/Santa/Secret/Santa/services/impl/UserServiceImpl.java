package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    IUserRepo iUserRepo;

    @Override
    public List<User> getAllUsers() {
        try {
            return iUserRepo.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving all users", e);
            throw e;
        }
    }

    @Override
    public User findByUserid(int userid) {
        try {
            Optional<User> optionalLessor = iUserRepo.findById(userid);
            return optionalLessor.orElseThrow(() -> new EntityNotFoundException("User not found with id " + userid));
        } catch (EntityNotFoundException e) {
            logger.error("User not found with ID: {}", userid, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error while retrieving user with ID: {}", userid, e);
            throw e;
        }
    }

    @Override
    public User editByUserId(UserDTO userDTO, int userid) {
        try {
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
            throw new RuntimeException("User not found with id: " + userid);
        } catch (RuntimeException e) {
            logger.error("Error occurred while updating user with ID: {}", userid, e);
            throw e;
        }
    }

    @Override
    public User createUser(UserDTO userDTO) {
        try {
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setGroups(new ArrayList<>());
            return iUserRepo.save(user);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            throw e;
        }
    }

    @Override
    public boolean deleteUserByUserid(int userid) {
        try {
            if (iUserRepo.existsById(userid)) {
                iUserRepo.deleteById(userid);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", userid, e);
            return false;
        }
    }


}
