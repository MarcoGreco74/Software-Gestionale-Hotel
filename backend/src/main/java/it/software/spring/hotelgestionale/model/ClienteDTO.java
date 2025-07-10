package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    @NotBlank(message = "Nome obbligatorio")
    private String nome;
    
    @NotBlank(message = "Cognome obbligatorio")
    private String cognome;
    
    private LocalDate dataNascita;

    private String tipoDocumento;

    private String numeroDocumento;

    private LocalDate scadenzaDocumento;

    private String indirizzo;

    private String nazionalità;

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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getScadenzaDocumento() {
        return scadenzaDocumento;
    }

    public void setScadenzaDocumento(LocalDate scadenzaDocumento) {
        this.scadenzaDocumento = scadenzaDocumento;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNazionalità() {
        return nazionalità;
    }

    public void setNazionalità(String nazionalità) {
        this.nazionalità = nazionalità;
    }
    
}

