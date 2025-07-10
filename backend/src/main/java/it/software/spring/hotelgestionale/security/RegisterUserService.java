package it.software.spring.hotelgestionale.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.software.spring.hotelgestionale.model.Role;
import it.software.spring.hotelgestionale.model.User;
import it.software.spring.hotelgestionale.model.UserDTO;
import it.software.spring.hotelgestionale.repository.RoleRepository;
import it.software.spring.hotelgestionale.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegisterUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUserAccount(UserDTO userDTO) {
    if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
        throw new RuntimeException("Username già esistente: " + userDTO.getUsername());
    }
    Role ruolo = roleRepository.findByNome(userDTO.getRole())
            .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + userDTO.getRole()));
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setEmail(userDTO.getEmail());
    List<Role> ruoli = new ArrayList<>();
    ruoli.add(ruolo);
    user.setRole(ruoli);
    userRepository.save(user);
  }

}
