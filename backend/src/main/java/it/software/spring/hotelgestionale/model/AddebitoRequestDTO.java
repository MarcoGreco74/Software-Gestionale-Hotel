package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

public class AddebitoRequestDTO {

    private Integer soggiornoId;

    private Integer prodottoId;

    private Integer quantita;

    private LocalDate data;  

    private String descrizione;
         
    private Double prezzoUnitario;

    public Integer getSoggiornoId() {
        return soggiornoId;
    }

    public void setSoggiornoId(Integer soggiornoId) {
        this.soggiornoId = soggiornoId;
    }

    public Integer getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(Integer prodottoId) {
        this.prodottoId = prodottoId;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(Double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


}

