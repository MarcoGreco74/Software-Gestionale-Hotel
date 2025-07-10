package it.software.spring.hotelgestionale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Addebito;

@Repository
public interface AddebitoRepository extends JpaRepository<Addebito, Integer> {

    List<Addebito> findBySoggiornoId(Integer soggiornoId);

    List<Addebito> findBySoggiornoIdOrderByDataAsc(Integer soggiornoId);

    boolean existsBySoggiornoIdAndDataAndDescrizioneIgnoreCase(Integer soggiornoId, LocalDate data, String descrizione); // per chiusura


    Optional<Addebito> findBySoggiornoIdAndDataAndDescrizione(Integer soggiornoId, LocalDate data, String descrizione);

}
