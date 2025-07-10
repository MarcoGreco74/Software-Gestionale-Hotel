package it.software.spring.hotelgestionale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Conto;

@Repository
public interface ContoRepository extends JpaRepository<Conto, Integer> {

}
