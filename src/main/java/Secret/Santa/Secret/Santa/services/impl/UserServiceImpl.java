package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.mappers.UserMapper;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final IUserRepo iUserRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))); // Assign role - USER
    }


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
            return optionalLessor.orElseThrow(() -> new RuntimeException("User not found with id: " + userid));
        } catch (RuntimeException e) {
            logger.error("Error occurred while retrieving user with ID: {}", userid, e);
            throw e;
        }
    }

    @Override
    public UserDTO editByUserId(UserDTO userDTO, int userid) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        Optional<User> existingUser = iUserRepo.findById(userid);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user = userMapper.toUser(userDTO, user);
            iUserRepo.save(user);
            return userMapper.toUserDTO(user);
        }
        throw new EntityNotFoundException("User not found with id " + userid);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        try {
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            //user.setGroups(new ArrayList<>());
            return iUserRepo.save(user);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            throw e;
        }
    }

    @Override
    public boolean deleteUserByUserid(int userid) {
        Optional<User> optionalUser = iUserRepo.findById(userid);
        if (optionalUser.isPresent()) {
            try {
                iUserRepo.deleteById(userid);
                return true;
            } catch (Exception e) {
                logger.error("Error deleting user with ID: {}", userid, e);
                return false;
            }
        } else {
            logger.error("Attempted to delete a user that does not exist with ID: {}", userid);
            throw new EntityNotFoundException("User not found with id " + userid);
        }
    }


}
