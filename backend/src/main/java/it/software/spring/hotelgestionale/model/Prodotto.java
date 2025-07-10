package it.software.spring.hotelgestionale.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodotto")
    private Integer id;

    @OneToMany(mappedBy="prodotto")
    private List<Addebito> addebiti = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id_reparto", nullable= false)
    @JsonIgnoreProperties("prodotti")
    private Reparto reparto;

    private String nome;

    private Double prezzo;

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

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public List<Addebito> getAddebiti() {
        return addebiti;
    }

    public void setAddebiti(List<Addebito> addebiti) {
        this.addebiti = addebiti;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public void setReparto(Reparto reparto) {
        this.reparto = reparto;
    }

}
