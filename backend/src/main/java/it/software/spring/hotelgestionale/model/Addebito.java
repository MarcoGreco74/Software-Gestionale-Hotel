package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Addebito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_addebito")
    private Integer id;

    private Integer quantita;

    private Double importo;

    private LocalDate data;

    private String descrizione;

    @ManyToOne
    @JoinColumn(name="conto_id", nullable= false)
    private Conto conto;

    @ManyToOne
    @JoinColumn(name="prodotto_id", nullable= false)
    private Prodotto prodotto;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "soggiorno_id")
    private Soggiorno soggiorno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
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

    public Conto getConto() {
        return conto;
    }

    public void setConto(Conto conto) {
        this.conto = conto;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Soggiorno getSoggiorno() {
        return soggiorno;
    }

    public void setSoggiorno(Soggiorno soggiorno) {
        this.soggiorno = soggiorno;
    }

}
