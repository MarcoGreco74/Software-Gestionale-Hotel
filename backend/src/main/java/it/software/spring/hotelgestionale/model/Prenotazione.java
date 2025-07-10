package it.software.spring.hotelgestionale.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Integer id;

    @Column(name = "data_in", nullable = false)
    private LocalDate dataIn;

    @Column(name = "data_out", nullable = false)
    private LocalDate dataOut;

    @Column(name="tariffa", nullable = false)
    private Double tariffa;

    @Column(name = "pax")
    private Integer pax;

    @ManyToOne
    @JoinColumn(name="camera_id", nullable= false)
    private Camera camera;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable= false)
    private Cliente cliente;

    @OneToOne(mappedBy = "prenotazione")
    private Soggiorno soggiorno;
    
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

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Soggiorno getSoggiorno() {
        return soggiorno;
    }

    public void setSoggiorno(Soggiorno soggiorno) {
        this.soggiorno = soggiorno;
    }

    public Integer getPax() {
        return pax;
    }

    public void setPax(Integer pax) {
        this.pax = pax;
    }

    public Double getTariffa() {
        return tariffa;
    }

    public void setTariffa(Double tariffa) {
        this.tariffa = tariffa;
    }

}
