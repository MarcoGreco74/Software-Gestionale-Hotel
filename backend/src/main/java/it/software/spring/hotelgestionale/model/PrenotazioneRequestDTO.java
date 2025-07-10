package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class PrenotazioneRequestDTO {

    private Integer id;

    private Integer clienteId; //  se esiste già

    private ClienteDTO cliente; // se nuovo cliente

    @NotNull
    private Integer cameraId;

    @NotNull(message = "La data di arrivo è obbligatoria")
    @FutureOrPresent(message = "La data di arrivo deve essere oggi o successiva")
    private LocalDate dataIn;

    @NotNull(message = "La data di partenza è obbligatoria")
    @Future(message = "La data di partenza deve essere nel futuro")
    private LocalDate dataOut;

    @Min(value = 1, message = "Deve esserci almeno una persona")
    @Max(value = 3, message = "Massimo 3 pax consentite")
    private Integer pax;

    @NotNull
    private Double tariffa;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
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

}




