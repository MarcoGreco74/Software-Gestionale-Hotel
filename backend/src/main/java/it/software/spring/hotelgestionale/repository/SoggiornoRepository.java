package it.software.spring.hotelgestionale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Soggiorno;

@Repository
public interface SoggiornoRepository extends JpaRepository<Soggiorno, Integer> {

    Optional<Soggiorno> findByPrenotazioneId(Integer prenotazioneId);

    @Query("SELECT s FROM Soggiorno s WHERE s.attivo = true AND s.dataOut = :data")
    List<Soggiorno> trovaSoggiorniInPartenza(@Param("data") LocalDate data);

    @Query("SELECT s FROM Soggiorno s WHERE s.prenotazione.camera.id = :cameraId AND CURRENT_DATE BETWEEN s.prenotazione.dataIn AND s.prenotazione.dataOut")
    Optional<Soggiorno> findSoggiornoAttivoByCameraId(@Param("cameraId") Integer cameraId);

    List<Soggiorno> findByAttivoTrue();

    boolean existsByPrenotazioneIdAndAttivoTrue(Integer prenotazioneId);

    Optional<Soggiorno> findByPrenotazioneIdAndAttivoTrue(Integer idPrenotazione);

    @Query("SELECT s FROM Soggiorno s WHERE s.prenotazione.id = :id AND s.attivo = true")
    Optional<Soggiorno> findAttivoByPrenotazioneId(@Param("id") Integer prenotazioneId);

    @Query("SELECT s FROM Soggiorno s WHERE s.attivo = true")
    List<Soggiorno> findSoggiorniAttivi(); // per il planning

    @Query("""
    SELECT s FROM Soggiorno s 
    WHERE s.attivo = true 
    AND s.prenotazione.camera.numeroCamera = :numeroCamera
    """)
    Optional<Soggiorno> findByNumeroCameraAttiva(@Param("numeroCamera") Integer numeroCamera);

}
