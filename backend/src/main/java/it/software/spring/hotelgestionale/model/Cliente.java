package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cognome;

    private LocalDate data_nascita;

    private String documento_tipo;

    private String n_documento;

    private LocalDate scadenza_documento;

    private String indirizzo;

    private String nazionalita;

    @OneToMany(mappedBy = "cliente")
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getDocumento_tipo() {
        return documento_tipo;
    }

    public void setDocumento_tipo(String documento_tipo) {
        this.documento_tipo = documento_tipo;
    }

    public String getN_documento() {
        return n_documento;
    }

    public void setN_documento(String n_documento) {
        this.n_documento = n_documento;
    }

    public LocalDate getScadenza_documento() {
        return scadenza_documento;
    }

    public void setScadenza_documento(LocalDate scadenza_documento) {
        this.scadenza_documento = scadenza_documento;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

}
