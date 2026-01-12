package khadija.app.backend.service;

import khadija.app.backend.dto.UserDTO;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalUserDetailsService implements UserDetailsService {

    private final WebClient webClient;

    public ExternalUserDetailsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> users =
                webClient
                        .get()
                        .uri("/userRegistration/getAllRegisteredUsers")
                        .retrieve()
                        .bodyToFlux(UserDTO.class)
                        .collectList()
                        .block();

        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
