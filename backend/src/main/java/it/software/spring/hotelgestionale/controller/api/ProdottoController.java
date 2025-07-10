package it.software.spring.hotelgestionale.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.software.spring.hotelgestionale.model.Prodotto;
import it.software.spring.hotelgestionale.model.ProdottoDTO;
import it.software.spring.hotelgestionale.repository.ProdottoRepository;

@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @GetMapping
    public ResponseEntity<List<ProdottoDTO>> getAllProdotti() {
    List<Prodotto> prodotti = prodottoRepository.findAll();
    List<ProdottoDTO> dto = prodotti.stream()
        .map(ProdottoDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dto);
  }

}
