package it.software.spring.hotelgestionale.controller.api;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Addebito;
import it.software.spring.hotelgestionale.model.Conto;
import it.software.spring.hotelgestionale.model.Prodotto;
import it.software.spring.hotelgestionale.model.Soggiorno;
import it.software.spring.hotelgestionale.repository.AddebitoRepository;
import it.software.spring.hotelgestionale.repository.ContoRepository;
import it.software.spring.hotelgestionale.repository.ProdottoRepository;
import it.software.spring.hotelgestionale.repository.SoggiornoRepository;

@RestController
@RequestMapping("/api/chiusura")
public class ChiusuraController {

    @Autowired
    private AddebitoRepository addebitoRepository;

    @Autowired
    private SoggiornoRepository soggiornoRepository;

    @Autowired
    private ContoRepository contoRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @PostMapping("/chiusura-giornata")
    public ResponseEntity<?> chiusuraGiornata() {
    List<Soggiorno> soggiorniAttivi = soggiornoRepository.findSoggiorniAttivi();
    int addebitiEffettuati = 0;
    for (Soggiorno s : soggiorniAttivi) {
       LocalDate dataPernottamento = LocalDate.now(ZoneId.of("Europe/Rome")).minusDays(1);
    if (!dataPernottamento.isBefore(s.getDataIn()) && dataPernottamento.isBefore(s.getDataOut())) {
        boolean giàAddebitato = addebitoRepository
            .existsBySoggiornoIdAndDataAndDescrizioneIgnoreCase(s.getId(), dataPernottamento, "Pernottamento");
        if (giàAddebitato) continue;
        Prodotto pernottamentoProdotto = prodottoRepository.findByNomeIgnoreCase("Pernottamento")
            .orElseThrow(() -> new RuntimeException("Prodotto 'Pernottamento' non trovato"));
        Addebito pernottamento = new Addebito();
        pernottamento.setSoggiorno(s);
        pernottamento.setData(dataPernottamento);
        pernottamento.setDescrizione("Pernottamento");
        pernottamento.setQuantita(1);
        pernottamento.setImporto(s.getPrenotazione().getTariffa());
        pernottamento.setConto(s.getConto()); 
        pernottamento.setProdotto(pernottamentoProdotto);
        addebitoRepository.save(pernottamento);
        Conto conto = s.getConto();
        conto.setTotale(conto.getTotale() + pernottamento.getImporto());
        contoRepository.save(conto);
        addebitiEffettuati++;
     }
   }
     return ResponseEntity.ok("Chiusura giornaliera completata. Addebiti effettuati: " + addebitiEffettuati);
  }

}
