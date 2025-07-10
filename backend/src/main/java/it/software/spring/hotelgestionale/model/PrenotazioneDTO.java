package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PrenotazioneDTO {

    @NotNull
    private Integer id;

    @NotBlank(message = "Nome cliente obbligatorio")
    private String nome;

    @NotBlank(message = "Cognome cliente obbligatorio")
    private String cognome;

    @NotNull(message = "Data di arrivo obbligatoria")
    @FutureOrPresent(message = "La data di arrivo deve essere oggi o successiva")
    private LocalDate dataIn;

    @NotNull(message = "Data di partenza obbligatoria")
    @Future(message = "La data di partenza deve essere nel futuro")
    private LocalDate dataOut;

    private Camera camera;

    private Integer cameraId;

    @NotNull(message = "Numero camera obbligatorio")
    private Integer numeroCamera;

    @NotBlank(message = "Tipologia camera obbligatoria")
    private String tipologia;

    @NotBlank(message = "Categoria camera obbligatoria")
    private String categoria;

    private Integer clienteIdEsistente;

    private Integer pax;

    private Double tariffa;

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

    public Integer getNumeroCamera() {
        return numeroCamera;
    }

    public void setNumeroCamera(Integer numeroCamera) {
        this.numeroCamera = numeroCamera;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getClienteIdEsistente() {
        return clienteIdEsistente;
    }

    public void setClienteIdEsistente(Integer clienteIdEsistente) {
        this.clienteIdEsistente = clienteIdEsistente;
    }

    public Integer getPax() {
        return pax;
    }

    public void setPax(Integer pax) {
        this.pax = pax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTariffa() {
        return tariffa;
    }

    public void setTariffa(Double tariffa) {
        this.tariffa = tariffa;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

}


