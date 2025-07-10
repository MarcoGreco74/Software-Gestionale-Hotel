package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Soggiorno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_soggiorno")
    private Integer id;

    @Column(name = "data_in", nullable = false)
    private LocalDate dataIn;

    @Column(name = "data_out", nullable = false)
    private LocalDate dataOut;

    @Column(nullable = false)
    private boolean attivo = true;

    @OneToOne
    @JoinColumn(name="prenotazione_id", nullable= false)
    private Prenotazione prenotazione;

    @OneToOne(mappedBy = "soggiorno", cascade = CascadeType.ALL)
    private Conto conto;

    @OneToMany(mappedBy = "soggiorno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addebito> addebiti = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    public Conto getConto() {
        return conto;
    }

    public void setConto(Conto conto) {
        this.conto = conto;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public List<Addebito> getAddebiti() {
        return addebiti;
    }

    public void setAddebiti(List<Addebito> addebiti) {
        this.addebiti = addebiti;
    }

}
