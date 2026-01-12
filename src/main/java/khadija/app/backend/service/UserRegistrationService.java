package khadija.app.backend.service;

import khadija.app.backend.dto.UserDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface UserRegistrationService {

    UserDTO registerUser(@Valid UserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(long userId);

    Void deleteUserById(long userId);

    UserDTO updateUser(Long id, @Valid UserDTO user);
}
