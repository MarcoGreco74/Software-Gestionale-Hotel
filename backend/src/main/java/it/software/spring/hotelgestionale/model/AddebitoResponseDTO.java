package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

public class AddebitoResponseDTO {

    private Integer id;
    private LocalDate data;
    private String descrizione;
    private Integer quantita;
    private Double prezzoUnitario;
    private Double importoTotale;
    private String nomeProdotto;
    private String nomeReparto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(Double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public Double getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(Double importoTotale) {
        this.importoTotale = importoTotale;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getNomeReparto() {
        return nomeReparto;
    }

    public void setNomeReparto(String nomeReparto) {
        this.nomeReparto = nomeReparto;
    }

}

