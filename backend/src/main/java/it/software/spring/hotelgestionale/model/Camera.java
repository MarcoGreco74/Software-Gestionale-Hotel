package it.software.spring.hotelgestionale.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camera")
    private Integer id;

    @Column(name = "n_camera")
    private Integer numeroCamera;

    private String tipologia;

    private Double tariffa;

    private String categoria;

    private Boolean occupata = false;

    @OneToMany(mappedBy = "camera")
    @JsonIgnore
    private List<Prenotazione> prenotazioni = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Double getTariffa() {
        return tariffa;
    }

    public void setTariffa(Double tariffa) {
        this.tariffa = tariffa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getOccupata() {
        return occupata;
    }

    public void setOccupata(Boolean occupata) {
        this.occupata = occupata;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public Integer getNumeroCamera() {
        return numeroCamera;
    }

    public void setNumeroCamera(Integer numeroCamera) {
        this.numeroCamera = numeroCamera;
    }

}
