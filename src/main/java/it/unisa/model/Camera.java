package it.unisa.model;

public class Camera {
    private int id;
    private String nome;
    private String descrizione;
    private String extra;
    private double prezzo;
    private String immagini;
    private int idCitta;
    private String nomeCitta;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public String getImmagini() { return immagini; }
    public void setImmagini(String immagini) { this.immagini = immagini; }
    public int getIdCitta() { return idCitta; }
    public void setIdCitta(int idCitta) { this.idCitta = idCitta; }
    public String getNomeCitta() { return nomeCitta; }
    public void setNomeCitta(String nomeCitta) { this.nomeCitta = nomeCitta; }
}