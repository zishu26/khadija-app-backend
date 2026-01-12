package khadija.app.backend.service;

import khadija.app.backend.dao.UserRepository;
import khadija.app.backend.dto.UserDTO;
import khadija.app.backend.exception.DetailCannotBeUpdated;
import khadija.app.backend.exception.UserNotFoundException;
import khadija.app.backend.model.User;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(@Valid UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        User user = convertToEntity(userDTO);

        // --- HASH PASSWORD BEFORE SAVING ---
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public UserDTO getUserById(long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return convertToDTO(user);
    }

    @Override
    public Void deleteUserById(long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        userRepository.delete(user);
        return null;
    }

    // Helper methods for conversion

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword()) // TODO: Create a new API to store hashed passwords
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    private User convertToEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }

    public UserDTO updateUser(Long id, @Valid UserDTO user) {
        User userEntity =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        if (!(userEntity.getEmail().equals(user.getEmail()))) {
            throw new DetailCannotBeUpdated("Email cannot be changed");
        }
        if (!(userEntity.getUsername().equals(user.getUsername()))) {
            throw new DetailCannotBeUpdated("Username cannot be changed");
        }
        if (userEntity.getPhone() != user.getPhone() && user.getPhone() != 0) {
            throw new DetailCannotBeUpdated("Phone cannot be changed");
        }
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        User updatedUser = userRepository.save(userEntity);
        return convertToDTO(updatedUser);
    }
}
