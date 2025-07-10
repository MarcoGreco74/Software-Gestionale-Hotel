package it.software.spring.hotelgestionale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.software.spring.hotelgestionale.model.Role;
import it.software.spring.hotelgestionale.model.User;
import it.software.spring.hotelgestionale.model.UserDTO;
import it.software.spring.hotelgestionale.repository.RoleRepository;
import it.software.spring.hotelgestionale.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username già esistente");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role ruolo = roleRepository.findByNome(userDTO.getRole())
            .orElseThrow(() -> new RuntimeException("Ruolo non trovato: " + userDTO.getRole()));
        user.getRole().add(ruolo);
        userRepository.save(user);
    }
}


