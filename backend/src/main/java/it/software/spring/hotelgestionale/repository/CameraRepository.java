package it.software.spring.hotelgestionale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.software.spring.hotelgestionale.model.Camera;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {

    Optional<Camera> findByNumeroCamera(int numero);

    @Query("""
    SELECT c FROM Camera c WHERE c.id NOT IN (
        SELECT p.camera.id FROM Prenotazione p
        WHERE :dataIn < p.dataOut AND :dataOut > p.dataIn )
""")

    List<Camera> findCamereDisponibili(
    @Param("dataIn") LocalDate dataIn,
    @Param("dataOut") LocalDate dataOut
   );

}
