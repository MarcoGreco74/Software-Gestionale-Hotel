package it.software.spring.hotelgestionale.model;

public class ProdottoDTO {

    private Integer id;
    private String nome;
    private Double prezzo;
    private String reparto;

    public ProdottoDTO() {

    }

    public ProdottoDTO(Integer id, String nome, Double prezzo, String reparto) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.reparto = reparto;
    }

    // Costruttore da entità Prodotto
    public ProdottoDTO(Prodotto prodotto) {
        this.id = prodotto.getId();
        this.nome = prodotto.getNome();
        this.prezzo = prodotto.getPrezzo();
        this.reparto = prodotto.getReparto().getNome_reparto(); // attenzione: metodo esatto del tuo model
    }

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

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }
    
}

