package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.mappers.UserMapper;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IUserRepo;
import Secret.Santa.Secret.Santa.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;
    private final IUserRepo iUserRepo;

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
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
//        user.setGroups(new ArrayList<>());
        return iUserRepo.save(user);
    }

    @Override
    public boolean deleteUserByUserid(int userid) {
        Optional<User> optionalGroup = iUserRepo.findById(userid);
        if (optionalGroup.isPresent()) {
            try {
                iUserRepo.deleteById(userid);
            } catch (Exception exception) {
                return false;
            }
            return true;
        }
        throw new EntityNotFoundException("User not found with id " + userid);
    }


}
