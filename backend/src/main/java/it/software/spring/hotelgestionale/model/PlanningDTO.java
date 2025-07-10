package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

public class PlanningDTO {

    private Integer prenotazioneId;

    private Integer soggiornoId;

    private String cliente;

    private int numeroCamera;

    private LocalDate dataIn;

    private LocalDate dataOut;

    private String tipo; // "PRENOTAZIONE" o "SOGGIORNO"

    public PlanningDTO(Prenotazione p, String tipo) {
        this.prenotazioneId = p.getId();
        this.cliente = p.getCliente().getCognome();
        this.numeroCamera = p.getCamera().getNumeroCamera();
        this.dataIn = p.getDataIn();
        this.dataOut = p.getDataOut();
        this.tipo = tipo;
    }

    public PlanningDTO(Soggiorno s, String tipo) {
    this.soggiornoId = s.getId(); 
    Prenotazione p = s.getPrenotazione();
    if (p != null) {
        this.prenotazioneId = p.getId(); 
        this.cliente = p.getCliente().getCognome();
        this.numeroCamera = p.getCamera().getNumeroCamera();
        this.dataIn = s.getDataIn(); 
        this.dataOut = s.getDataOut(); 
    }
    this.tipo = tipo;
}

    public PlanningDTO(Camera c, String tipo){
        this.numeroCamera = c.getNumeroCamera();
        this.tipo = tipo;
    }

    public Integer getPrenotazioneId() {
        return prenotazioneId;
    }

    public void setPrenotazioneId(Integer prenotazioneId) {
        this.prenotazioneId = prenotazioneId;
    }

    public Integer getSoggiornoId() {
        return soggiornoId;
    }

    public void setSoggiornoId(Integer soggiornoId) {
        this.soggiornoId = soggiornoId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getNumeroCamera() {
        return numeroCamera;
    }

    public void setNumeroCamera(int numeroCamera) {
        this.numeroCamera = numeroCamera;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}


