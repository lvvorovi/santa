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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;
    private final IUserRepo iUserRepo;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = iUserRepo.findAll();

        return users.stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }


    @Override
    public UserDTO findByUserid(int userid) {
        Optional<User> optionalUser = iUserRepo.findById(userid);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userMapper.toUserDTO(user);
        }

        throw new EntityNotFoundException("User not found with id " + userid);
    }

    @Override
    public UserDTO editByUserId(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        if (userDTO.getUserId() == null){
            throw new IllegalArgumentException("This user does not have ID");
        }
        Optional<User> existingUser = iUserRepo.findById(userDTO.getUserId());
        if (existingUser.isPresent()) {
            User user = userMapper.toUser(userDTO);
            User updatedUser = iUserRepo.save(user);
            return userMapper.toUserDTO(updatedUser);
        }
        throw new EntityNotFoundException("User not found with id " + userDTO.getUserId());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User savedUser = iUserRepo.save(user);
        return userMapper.toUserDTO(savedUser);
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
