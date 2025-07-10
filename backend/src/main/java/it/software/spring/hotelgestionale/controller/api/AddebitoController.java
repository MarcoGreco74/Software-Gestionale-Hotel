package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Addebito;
import it.software.spring.hotelgestionale.model.AddebitoRequestDTO;
import it.software.spring.hotelgestionale.model.AddebitoResponseDTO;
import it.software.spring.hotelgestionale.model.Conto;
import it.software.spring.hotelgestionale.model.Prodotto;
import it.software.spring.hotelgestionale.model.Soggiorno;
import it.software.spring.hotelgestionale.repository.AddebitoRepository;
import it.software.spring.hotelgestionale.repository.CameraRepository;
import it.software.spring.hotelgestionale.repository.ContoRepository;
import it.software.spring.hotelgestionale.repository.ProdottoRepository;
import it.software.spring.hotelgestionale.repository.SoggiornoRepository;

@RestController
@RequestMapping("/api/addebiti")
public class AddebitoController {

    @Autowired
    private AddebitoRepository addebitoRepository;

    @Autowired
    private SoggiornoRepository soggiornoRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private ContoRepository contoRepository;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> creaAddebito(@RequestBody AddebitoRequestDTO dto) {
    if (dto.getSoggiornoId() == null) {
        return ResponseEntity.badRequest().body("Soggiorno ID mancante");
    }
    Optional<Soggiorno> optionalSoggiorno = soggiornoRepository.findById(dto.getSoggiornoId());
    if (optionalSoggiorno.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soggiorno non trovato");
    }
    Soggiorno soggiorno = optionalSoggiorno.get();
    if (soggiorno.getConto() == null) {
        return ResponseEntity.badRequest().body("Conto non associato al soggiorno");
    }
    Optional<Prodotto> optionalProdotto = prodottoRepository.findById(dto.getProdottoId());
    if (optionalProdotto.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prodotto non trovato");
    }
    Prodotto prodotto = optionalProdotto.get();
    Addebito addebito = new Addebito();
    addebito.setSoggiorno(soggiorno);
    addebito.setConto(soggiorno.getConto());
    addebito.setProdotto(prodotto);
    addebito.setQuantita(dto.getQuantita());
    addebito.setData(dto.getData());
    addebito.setDescrizione(dto.getDescrizione());
    if ("Pernottamento".equalsIgnoreCase(prodotto.getNome())) {
            addebito.setImporto(soggiorno.getPrenotazione().getTariffa() * dto.getQuantita());
        } else {
            addebito.setImporto(dto.getPrezzoUnitario() * dto.getQuantita());
        }
    Addebito salvato = addebitoRepository.save(addebito);
    // DTO di risposta
    AddebitoResponseDTO responseDTO = new AddebitoResponseDTO();
    responseDTO.setId(salvato.getId());
    responseDTO.setData(salvato.getData());
    responseDTO.setDescrizione(salvato.getDescrizione());
    responseDTO.setQuantita(salvato.getQuantita());
    responseDTO.setPrezzoUnitario(dto.getPrezzoUnitario());
    responseDTO.setImportoTotale(salvato.getImporto());
    responseDTO.setNomeProdotto(prodotto.getNome());
    responseDTO.setNomeReparto(prodotto.getReparto().getNome_reparto());
    Conto conto = soggiorno.getConto();
    double nuovoTotale = conto.getTotale() + addebito.getImporto();
    conto.setTotale(nuovoTotale);
    contoRepository.save(conto);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/storna/{idAddebito}")  // storna creando l'addebito in negativo -- in uso
  public ResponseEntity<?> stornaAddebito(@PathVariable Integer idAddebito) {
    Addebito originale = addebitoRepository.findById(idAddebito)
        .orElseThrow(() -> new RuntimeException("Addebito non trovato"));
    Addebito storno = new Addebito();
    storno.setSoggiorno(originale.getSoggiorno());
    storno.setConto(originale.getConto());
    storno.setProdotto(originale.getProdotto());
    storno.setQuantita(originale.getQuantita());
    storno.setData(LocalDate.now());
    storno.setDescrizione("Storno di addebito ID " + originale.getId());
    storno.setImporto(-originale.getImporto());
    Addebito salvato = addebitoRepository.save(storno);
    // aggiorna il totale nel conto
    Conto conto = originale.getConto();
    conto.setTotale(conto.getTotale() - originale.getImporto());
    contoRepository.save(conto);
    return ResponseEntity.ok(salvato);
  }

  @DeleteMapping("/delete/{idAddebito}") // storna cancellando definitivamente l'addebito --in disuso
  public ResponseEntity<Void> deletexId(@PathVariable("idAddebito") Integer idAddebito) {
    Addebito addebito = addebitoRepository.findById(idAddebito)
        .orElseThrow(() -> new RuntimeException("Addebito non trovato"));
    Conto conto = addebito.getConto();
    conto.setTotale(conto.getTotale() - addebito.getImporto());
    contoRepository.save(conto);
    addebitoRepository.deleteById(idAddebito);
    return ResponseEntity.ok().build();
  }

 @PutMapping("/trasferisci-da-camera/{idAddebito}/{numeroCameraDestinazione}")
    public ResponseEntity<?> trasferisciAddebitoByNumeroCamera(
        @PathVariable Integer idAddebito,
        @PathVariable Integer numeroCameraDestinazione
    ) {
    Optional<Addebito> optionalAddebito = addebitoRepository.findById(idAddebito);
    if (optionalAddebito.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Addebito non trovato");
    }
    Optional<Soggiorno> optionalSoggiornoDest = soggiornoRepository.findByNumeroCameraAttiva(numeroCameraDestinazione);
    if (optionalSoggiornoDest.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Nessun soggiorno attivo per questa camera");
    }
    Soggiorno nuovoSoggiorno = optionalSoggiornoDest.get();
    if (nuovoSoggiorno.getConto() == null) {
        return ResponseEntity.badRequest().body("Il nuovo soggiorno non ha un conto associato");
    }
    Addebito addebito = optionalAddebito.get();
    addebito.setSoggiorno(nuovoSoggiorno);
    addebito.setConto(nuovoSoggiorno.getConto());
    addebitoRepository.save(addebito);
    return ResponseEntity.ok("Addebito trasferito correttamente");
  }

  @GetMapping("/conto/soggiorno/{idSoggiorno}")
  public ResponseEntity<Double> getTotaleContoBySoggiorno(@PathVariable Integer idSoggiorno) {
    Optional<Soggiorno> soggiornoOpt = soggiornoRepository.findById(idSoggiorno);
    if (soggiornoOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    Soggiorno soggiorno = soggiornoOpt.get();
    Conto conto = soggiorno.getConto();
    if (conto == null) {
        return ResponseEntity.ok(0.0);
    }
    return ResponseEntity.ok(conto.getTotale());
  }

}

