package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.PlanningDTO;
import it.software.spring.hotelgestionale.repository.CameraRepository;
import it.software.spring.hotelgestionale.repository.PrenotazioneRepository;
import it.software.spring.hotelgestionale.repository.SoggiornoRepository;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private SoggiornoRepository soggiornoRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @GetMapping
    public ResponseEntity<Map<String, List<PlanningDTO>>> getPlanning() {
        List<PlanningDTO> prenotazioni = prenotazioneRepository.findFuturePrenotazioni(LocalDate.now())
            .stream().map(p -> new PlanningDTO(p, "PRENOTAZIONE")).toList();
        List<PlanningDTO> soggiorni = soggiornoRepository.findSoggiorniAttivi()
            .stream().map(s -> new PlanningDTO(s, "SOGGIORNO")).toList();
        List<PlanningDTO> camere = cameraRepository.findAll()
             .stream().map(c -> new PlanningDTO(c, "CAMERA")).toList();
        Map<String, List<PlanningDTO>> result = new HashMap<>();
        result.put("prenotazioni", prenotazioni);
        result.put("soggiorni", soggiorni);
        result.put("camere", camere);
        return ResponseEntity.ok(result);
    }
}

