package khadija.app.backend.controller;

import khadija.app.backend.dto.UserDTO;
import khadija.app.backend.service.UserRegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userRegistration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userRegistrationService.registerUser(dto));
    }

    @GetMapping("/getAllRegisteredUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userRegistrationService.getAllUsers());
    }

    @GetMapping("/search/registeredUser/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRegistrationService.getUserById(id));
    }

    @DeleteMapping("/delete/registeredUser/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        userRegistrationService.deleteUserById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PutMapping("/update/registeredUser/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserDTO dto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(userRegistrationService.updateUser(id, dto));
    }
}
