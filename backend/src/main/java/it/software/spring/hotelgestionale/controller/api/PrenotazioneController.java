package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Camera;
import it.software.spring.hotelgestionale.model.Cliente;
import it.software.spring.hotelgestionale.model.Prenotazione;
import it.software.spring.hotelgestionale.model.PrenotazioneDTO;
import it.software.spring.hotelgestionale.model.PrenotazioneRequestDTO;
import it.software.spring.hotelgestionale.repository.CameraRepository;
import it.software.spring.hotelgestionale.repository.ClienteRepository;
import it.software.spring.hotelgestionale.repository.PrenotazioneRepository;
import it.software.spring.hotelgestionale.repository.SoggiornoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prenotazione/v2")
public class PrenotazioneController {

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;   

    @Autowired
    private SoggiornoRepository soggiornoRepository;
    
    @GetMapping("/listaPrenotazioni")
    public ResponseEntity<List<PrenotazioneDTO>> index() {
    List<Prenotazione> prenotazioni = prenotazioneRepository.findAllOrderByDataInDesc();
    List<PrenotazioneDTO> prenotazioneDTOs = new ArrayList<>();
    for (Prenotazione p : prenotazioni) {
        prenotazioneDTOs.add(toDTO(p));
      }
    return ResponseEntity.ok(prenotazioneDTOs);
    }

    @PostMapping("/creaPrenotazioni")
    public ResponseEntity<Prenotazione> creaPrenotazione(@Valid @RequestBody PrenotazioneRequestDTO dto) {
    List<Prenotazione> esistenti = prenotazioneRepository.trovaPrenotazioniSovrapposte(dto.getCameraId(), dto.getDataIn(), dto.getDataOut(), dto.getId());
    if (!esistenti.isEmpty()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(null); 
    }
    Cliente cliente;
    if (dto.getClienteId() != null) {
        cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
    } else {
        Cliente nuovo = new Cliente();
        nuovo.setNome(dto.getCliente().getNome());
        nuovo.setCognome(dto.getCliente().getCognome());
        cliente = clienteRepository.save(nuovo);
    }
    Camera camera = cameraRepository.findById(dto.getCameraId())
        .orElseThrow(() -> new RuntimeException("Camera non trovata"));
    Prenotazione prenotazione = new Prenotazione();
    prenotazione.setCliente(cliente);
    prenotazione.setCamera(camera);
    prenotazione.setDataIn(dto.getDataIn());
    prenotazione.setDataOut(dto.getDataOut());
    prenotazione.setTariffa(dto.getTariffa());
    prenotazione.setPax(dto.getPax());
    prenotazione = prenotazioneRepository.save(prenotazione);
    return ResponseEntity.status(HttpStatus.CREATED).body(prenotazione);
  }

private PrenotazioneDTO toDTO(Prenotazione p) {
    PrenotazioneDTO dto = new PrenotazioneDTO();
    dto.setId(p.getId());
    dto.setNome(p.getCliente().getNome());
    dto.setCognome(p.getCliente().getCognome());
    dto.setNumeroCamera(p.getCamera().getNumeroCamera());
    dto.setTipologia(p.getCamera().getTipologia());
    dto.setCategoria(p.getCamera().getCategoria());
    dto.setDataIn(p.getDataIn());
    dto.setDataOut(p.getDataOut());
    dto.setPax(p.getPax());
    dto.setTariffa(p.getTariffa());
    return dto;
  }

    @GetMapping("/arriviOggi")
    public ResponseEntity<List<PrenotazioneDTO>> arriviOggi() {
    LocalDate oggi = LocalDate.now();
    List<Prenotazione> prenotazioni = prenotazioneRepository.trovaPrenotazioniInArrivoOggiNonCheckin(oggi);
    List<PrenotazioneDTO> prenotazioneDTOs = new ArrayList<>();
    for (Prenotazione p : prenotazioni) {
        prenotazioneDTOs.add(toDTO(p));
      }
    return ResponseEntity.ok(prenotazioneDTOs);
    }

    @GetMapping("/gestionePreno/{id}")
    public ResponseEntity<?> gestionePreno(@PathVariable Integer id) {
    Optional<Prenotazione> optPreno = prenotazioneRepository.findById(id);
    if (!optPreno.isPresent()) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("errore", "Prenotazione con ID " + id + " non trovata"));
    }
    PrenotazioneDTO dto = toDTO(optPreno.get()); 
    return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PrenotazioneRequestDTO dto) {
    Optional<Prenotazione> opt = prenotazioneRepository.findById(id);
    if (opt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    Camera camera = cameraRepository.findById(dto.getCameraId())
        .orElseThrow(() -> new RuntimeException("Camera non trovata"));
    List<Prenotazione> esistenti = prenotazioneRepository.trovaPrenotazioniSovrapposte(
        camera.getNumeroCamera(), dto.getDataIn(), dto.getDataOut(), id);
    if (!esistenti.isEmpty()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Camera già occupata nel periodo selezionato");
    }

    Prenotazione preno = opt.get();
    preno.setDataIn(dto.getDataIn());
    preno.setDataOut(dto.getDataOut());
    preno.setPax(dto.getPax());
    preno.setTariffa(dto.getTariffa());
    preno.setCamera(camera);
    Cliente cliente = preno.getCliente();
    cliente.setNome(dto.getCliente().getNome());
    cliente.setCognome(dto.getCliente().getCognome());
    clienteRepository.save(cliente);
    Prenotazione aggiornata = prenotazioneRepository.save(preno);
    soggiornoRepository.findByPrenotazioneId(aggiornata.getId())
        .ifPresent(soggiorno -> {
            soggiorno.setDataOut(dto.getDataOut());
            soggiornoRepository.save(soggiorno);
        });
    return ResponseEntity.ok(aggiornata);
  }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    if (!prenotazioneRepository.existsById(id)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    prenotazioneRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
   }

}