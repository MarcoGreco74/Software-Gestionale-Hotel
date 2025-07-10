package it.software.spring.hotelgestionale.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {

  @Query("SELECT p FROM Prenotazione p ORDER BY p.dataIn DESC")
  List<Prenotazione> findAllOrderByDataInDesc();

@Query("""
    SELECT p FROM Prenotazione p
    WHERE p.camera.id = :cameraId
    AND (
        (:id IS NULL OR p.id <> :id)
    )
    AND p.dataIn < :dataOut
    AND p.dataOut > :dataIn
""")
List<Prenotazione> trovaPrenotazioniSovrapposte(
    @Param("cameraId") Integer cameraId,
    @Param("dataIn") LocalDate dataIn,
    @Param("dataOut") LocalDate dataOut,
    @Param("id") Integer id // può essere null
);

    @Query("SELECT p FROM Prenotazione p WHERE p.dataIn = :oggi")
    List<Prenotazione> trovaPrenotazioniConArrivoOggi(@Param("oggi") LocalDate oggi);

    @Query("""
  SELECT p FROM Prenotazione p
  WHERE p.dataIn = :oggi
    AND NOT EXISTS (
      SELECT s FROM Soggiorno s
      WHERE s.prenotazione.id = p.id AND s.attivo = true
    )
""")
List<Prenotazione> trovaPrenotazioniInArrivoOggiNonCheckin(@Param("oggi") LocalDate oggi);

@Query("SELECT p FROM Prenotazione p WHERE p.dataIn >= :oggi AND NOT EXISTS (SELECT s FROM Soggiorno s WHERE s.prenotazione = p AND s.attivo = true) ORDER BY p.dataIn")
List<Prenotazione> findFuturePrenotazioni(@Param("oggi") LocalDate oggi); // per il planning

}
