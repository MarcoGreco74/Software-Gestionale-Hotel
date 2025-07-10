package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;
import java.util.List;

public class SoggiornoDettaglioDTO {

    private Integer idSoggiorno;

    private Integer idPrenotazione;

    private Integer idCamera;

    private String nome;

    private String cognome;

    private Integer numeroCamera;

    private Double tariffa;

    private LocalDate dataIn;

    private LocalDate dataOut;

    private Integer pax;

    private List<AddebitoResponseDTO> addebiti; 

    public Integer getIdSoggiorno() {
        return idSoggiorno;
    }

    public void setIdSoggiorno(Integer idSoggiorno) {
        this.idSoggiorno = idSoggiorno;
    }

    public Integer getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Integer idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Integer getNumeroCamera() {
        return numeroCamera;
    }

    public void setNumeroCamera(Integer numeroCamera) {
        this.numeroCamera = numeroCamera;
    }

    public Double getTariffa() {
        return tariffa;
    }

    public void setTariffa(Double tariffa) {
        this.tariffa = tariffa;
    }

    public LocalDate getDataIn() {
        return dataIn;
    }

    public void setDataIn(LocalDate dataIn) {
        this.dataIn = dataIn;
    }

    public LocalDate getDataOut() {
        return dataOut;
    }

    public void setDataOut(LocalDate dataOut) {
        this.dataOut = dataOut;
    }

    public Integer getPax() {
        return pax;
    }

    public void setPax(Integer pax) {
        this.pax = pax;
    }

    public Integer getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(Integer idCamera) {
        this.idCamera = idCamera;
    }

    public List<AddebitoResponseDTO> getAddebiti() {
        return addebiti;
    }

    public void setAddebiti(List<AddebitoResponseDTO> addebiti) {
        this.addebiti = addebiti;
    }

}

