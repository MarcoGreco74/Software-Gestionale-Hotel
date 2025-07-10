package it.software.spring.hotelgestionale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Documentofiscale;

@Repository
public interface DocumentoFiscaleRepository extends JpaRepository<Documentofiscale, Integer> {

}
