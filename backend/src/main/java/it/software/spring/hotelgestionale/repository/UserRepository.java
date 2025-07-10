package it.software.spring.hotelgestionale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); 

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.nome = :role") 
    List<User> findByRole(@Param("role") String role); // si può eliminare e sostituire con List<User> findByRoleNome(String role);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.nome = 'OPERATORE' AND u.id = :id")
    Optional<User> findOperatoreById(@Param("id") Integer id);

    public Optional<User> findByUsername(String username);

    List<User> findByRoleNome(String role);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.nome = 'OPERATORE'")
    List<User> findAllOperatori(); // si può eliminare e sostituire con List<User> findByRoleNome(String nome);

}
