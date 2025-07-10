package it.software.spring.hotelgestionale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    Optional<Prodotto> findByNomeIgnoreCase(String nome);


}
