package it.software.spring.hotelgestionale.model;

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
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conto")
    private Integer id;

    private Double totale;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="soggiorno_id", nullable= false)
    private Soggiorno soggiorno;

    @OneToOne(mappedBy = "conto")
    private Documentofiscale documentoFiscale;

    @OneToMany(mappedBy = "conto")
    private List<Addebito> addebiti = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public Soggiorno getSoggiorno() {
        return soggiorno;
    }

    public void setSoggiorno(Soggiorno soggiorno) {
        this.soggiorno = soggiorno;
    }

    public Documentofiscale getDocumentoFiscale() {
        return documentoFiscale;
    }

    public void setDocumentoFiscale(Documentofiscale documentoFiscale) {
        this.documentoFiscale = documentoFiscale;
    }

    public List<Addebito> getAddebiti() {
        return addebiti;
    }

    public void setAddebiti(List<Addebito> addebiti) {
        this.addebiti = addebiti;
    }

}
