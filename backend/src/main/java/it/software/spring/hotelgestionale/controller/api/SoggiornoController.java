package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Addebito;
import it.software.spring.hotelgestionale.model.AddebitoResponseDTO;
import it.software.spring.hotelgestionale.model.Camera;
import it.software.spring.hotelgestionale.model.Cliente;
import it.software.spring.hotelgestionale.model.Conto;
import it.software.spring.hotelgestionale.model.Prenotazione;
import it.software.spring.hotelgestionale.model.PrenotazioneDTO;
import it.software.spring.hotelgestionale.model.PrenotazioneRequestDTO;
import it.software.spring.hotelgestionale.model.Soggiorno;
import it.software.spring.hotelgestionale.model.SoggiornoDettaglioDTO;
import it.software.spring.hotelgestionale.repository.CameraRepository;
import it.software.spring.hotelgestionale.repository.ClienteRepository;
import it.software.spring.hotelgestionale.repository.ContoRepository;
import it.software.spring.hotelgestionale.repository.PrenotazioneRepository;
import it.software.spring.hotelgestionale.repository.SoggiornoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/soggiorno/v2")
public class SoggiornoController {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private SoggiornoRepository soggiornoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private ContoRepository contoRepository;

    @PostMapping("/checkin/{prenotazioneId}")
    public ResponseEntity<Soggiorno> checkIn(@PathVariable Integer prenotazioneId) {
    Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
        .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    if (prenotazione.getDataIn().isAfter(LocalDate.now())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(null);
    }
    boolean esisteSoggiorno = soggiornoRepository.existsByPrenotazioneIdAndAttivoTrue(prenotazioneId);
    if (esisteSoggiorno) {
        throw new RuntimeException("Soggiorno già attivo per questa prenotazione");
    }
    Camera camera = prenotazione.getCamera();
    camera.setOccupata(true);
    cameraRepository.save(camera); 
    prenotazione.setCamera(camera);
    prenotazioneRepository.save(prenotazione);
    Soggiorno soggiorno = new Soggiorno();
    soggiorno.setPrenotazione(prenotazione);
    soggiorno.setDataIn(LocalDate.now());
    soggiorno.setDataOut(prenotazione.getDataOut());
    soggiorno = soggiornoRepository.save(soggiorno); 
    Conto conto = new Conto();
    conto.setSoggiorno(soggiorno);
    conto.setTotale(0.0); 
    conto = contoRepository.save(conto);
    soggiorno.setConto(conto);
    soggiorno = soggiornoRepository.save(soggiorno); 
    return ResponseEntity.status(HttpStatus.CREATED).body(soggiorno);
  }

  @PostMapping("/creaWalkin")
public ResponseEntity<?> creaPrenotazione(@Valid @RequestBody PrenotazioneRequestDTO dto) {
    if (!dto.getDataIn().isEqual(LocalDate.now())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errore", "La data di check-in per un walk-in deve essere oggi."));
    }
    List<Prenotazione> esistenti = prenotazioneRepository.trovaPrenotazioniSovrapposte(
            dto.getCameraId(), dto.getDataIn(), dto.getDataOut(), dto.getId());
    if (!esistenti.isEmpty()) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errore", "La camera selezionata è già occupata in queste date."));
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
    camera.setOccupata(true);
    cameraRepository.save(camera);
    Prenotazione prenotazione = new Prenotazione();
    prenotazione.setCliente(cliente);
    prenotazione.setCamera(camera);
    prenotazione.setDataIn(dto.getDataIn());
    prenotazione.setDataOut(dto.getDataOut());
    prenotazione.setTariffa(dto.getTariffa());
    prenotazione.setPax(dto.getPax());
    prenotazione = prenotazioneRepository.save(prenotazione);
    Soggiorno soggiorno = new Soggiorno();
    soggiorno.setPrenotazione(prenotazione);
    soggiorno.setDataIn(LocalDate.now());
    soggiorno.setDataOut(dto.getDataOut());
    soggiorno.setAttivo(true);
    Conto conto = new Conto();
    conto.setSoggiorno(soggiorno);
    conto.setTotale(0.0);
    conto = contoRepository.save(conto);
    soggiorno.setConto(conto);
    soggiorno = soggiornoRepository.save(soggiorno);
    return ResponseEntity.status(HttpStatus.CREATED).body(prenotazione);
  }

    @GetMapping("/inCasa")
    public ResponseEntity<List<PrenotazioneDTO>> clientiInCasa() {
    List<Soggiorno> soggiorni = soggiornoRepository.findByAttivoTrue();
    List<PrenotazioneDTO> dtoList = new ArrayList<>();
    for (Soggiorno soggiorno : soggiorni) {
        Prenotazione p = soggiorno.getPrenotazione();
        PrenotazioneDTO dto = new PrenotazioneDTO();
        dto.setId(p.getId());
        dto.setNome(p.getCliente().getNome());
        dto.setCognome(p.getCliente().getCognome());
        dto.setNumeroCamera(p.getCamera().getNumeroCamera());
        dto.setCameraId(p.getCamera().getId());
        dto.setTipologia(p.getCamera().getTipologia());
        dto.setCategoria(p.getCamera().getCategoria());
        dto.setDataIn(p.getDataIn());
        dto.setDataOut(p.getDataOut());
        dto.setPax(p.getPax());
        dto.setTariffa(p.getTariffa());
        dtoList.add(dto);
      }
    return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/inPartenza")
    public ResponseEntity<List<PrenotazioneDTO>> inPartenza() {
    LocalDate oggi = LocalDate.now();
    List<Soggiorno> soggiorni = soggiornoRepository.trovaSoggiorniInPartenza(oggi);
    List<PrenotazioneDTO> dtoList = new ArrayList<>();
    for (Soggiorno soggiorno : soggiorni) {
        Prenotazione p = soggiorno.getPrenotazione();
        PrenotazioneDTO dto = new PrenotazioneDTO();
        dto.setId(p.getId());
        dto.setNome(p.getCliente().getNome());
        dto.setCognome(p.getCliente().getCognome());
        dto.setCameraId(p.getCamera().getId());
        dto.setNumeroCamera(p.getCamera().getNumeroCamera());
        dto.setTipologia(p.getCamera().getTipologia());
        dto.setCategoria(p.getCamera().getCategoria());
        dto.setDataIn(p.getDataIn());
        dto.setDataOut(p.getDataOut());
        dto.setPax(p.getPax());
        dto.setTariffa(p.getTariffa());
        dtoList.add(dto);
      }
    return ResponseEntity.ok(dtoList);
    }

  @PutMapping("/checkout/{id}")
   public ResponseEntity<?> checkOut(@PathVariable Integer id) {
    Optional<Soggiorno> optional = soggiornoRepository.findAttivoByPrenotazioneId(id);
    if (optional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("errore", "Soggiorno non trovato"));
    }
    Soggiorno soggiorno = optional.get();
    soggiorno.setAttivo(false);
    Camera camera = soggiorno.getPrenotazione().getCamera();
    camera.setOccupata(false);
    soggiornoRepository.save(soggiorno);
    cameraRepository.save(camera);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/checkinEffettuato/{prenotazioneId}")
   public ResponseEntity<Boolean> checkinEffettuato(@PathVariable Integer prenotazioneId) {
    Optional<Soggiorno> soggiornoOpt = soggiornoRepository.findByPrenotazioneIdAndAttivoTrue(prenotazioneId);
    boolean presente = soggiornoOpt.isPresent();
    return ResponseEntity.ok(presente);
  }

  @GetMapping("/dettaglioByPrenotazione/{idPrenotazione}")
    public ResponseEntity<SoggiornoDettaglioDTO> dettaglioByPrenotazione(@PathVariable Integer idPrenotazione) {
    Soggiorno soggiorno = soggiornoRepository.findByPrenotazioneIdAndAttivoTrue(idPrenotazione)
        .orElseThrow(() -> new RuntimeException("Soggiorno non trovato"));
    Prenotazione p = soggiorno.getPrenotazione();
    SoggiornoDettaglioDTO dto = new SoggiornoDettaglioDTO();
    dto.setIdSoggiorno(soggiorno.getId());
    dto.setIdPrenotazione(p.getId());
    dto.setNome(p.getCliente().getNome());
    dto.setCognome(p.getCliente().getCognome());
    dto.setNumeroCamera(p.getCamera().getNumeroCamera());
    dto.setTariffa(p.getTariffa());
    dto.setDataIn(p.getDataIn());
    dto.setDataOut(p.getDataOut());
    dto.setPax(p.getPax());
    List<AddebitoResponseDTO> addebitiDTO = new ArrayList<>();
    for (Addebito addebito : soggiorno.getAddebiti()) {
        AddebitoResponseDTO ad = new AddebitoResponseDTO();
        ad.setId(addebito.getId());
        ad.setData(addebito.getData());
        ad.setDescrizione(addebito.getDescrizione());
        ad.setQuantita(addebito.getQuantita());
        double prezzoUnitario = addebito.getQuantita() > 0
            ? addebito.getImporto() / addebito.getQuantita()
            : 0.0;
        ad.setPrezzoUnitario(prezzoUnitario);
        ad.setImportoTotale(addebito.getImporto());
        if (addebito.getProdotto() != null) {
            ad.setNomeProdotto(addebito.getProdotto().getNome());
            if (addebito.getProdotto().getReparto() != null) {
                ad.setNomeReparto(addebito.getProdotto().getReparto().getNome_reparto());
            }
        }
        addebitiDTO.add(ad);
    }
    dto.setAddebiti(addebitiDTO);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/prenotazioneAttivaByCamera/{cameraId}")
  public ResponseEntity<Integer> getPrenotazioneAttivaByCamera(@PathVariable Integer cameraId) {
    Optional<Soggiorno> soggiornoOpt = soggiornoRepository.findSoggiornoAttivoByCameraId(cameraId);
    if (soggiornoOpt.isPresent()) {
        Integer idPrenotazione = soggiornoOpt.get().getPrenotazione().getId();
        return ResponseEntity.ok(idPrenotazione);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

}
