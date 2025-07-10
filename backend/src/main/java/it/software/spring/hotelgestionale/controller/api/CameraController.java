package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Camera;
import it.software.spring.hotelgestionale.repository.CameraRepository;

@RestController
@RequestMapping("/api/camere/v2")
public class CameraController {

    @Autowired
    private CameraRepository cameraRepository;

    @GetMapping
    public ResponseEntity<List<Camera>> index(){
        List<Camera> camere = cameraRepository.findAll();
        return ResponseEntity.ok().body(camere);
    }

    @GetMapping("/disponibili")
    public ResponseEntity<List<Camera>> camereDisponibili(
        @RequestParam("dataIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataIn,
        @RequestParam("dataOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataOut) {
      if (dataIn == null || dataOut == null || dataOut.isBefore(dataIn)) {
        return ResponseEntity.badRequest().build();
       }
    List<Camera> disponibili = cameraRepository.findCamereDisponibili(dataIn, dataOut);
    return ResponseEntity.ok(disponibili);
  }

}
