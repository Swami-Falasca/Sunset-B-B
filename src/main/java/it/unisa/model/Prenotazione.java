package it.unisa.model;

import java.time.LocalDate;

public class Prenotazione {
	private int id;
	private String nomeUtente;
	private String cognomeUtente;
    private String citta;
    private String nomeBnb;
    private String immagineBnb;
    private LocalDate checkin;
    private LocalDate checkout;
    private int adulti;
    private int bambini;
    private int camere;
    private double prezzoTotale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNomeUtente() {
        return nomeUtente;
    }
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }
    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }
    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getNomeBnb() {
        return nomeBnb;
    }

    public void setNomeBnb(String nomeBnb) {
        this.nomeBnb = nomeBnb;
    }

    public String getImmagineBnb() {
        return immagineBnb;
    }

    public void setImmagineBnb(String immagineBnb) {
        this.immagineBnb = immagineBnb;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public int getAdulti() {
        return adulti;
    }

    public void setAdulti(int adulti) {
        this.adulti = adulti;
    }

    public int getBambini() {
        return bambini;
    }

    public void setBambini(int bambini) {
        this.bambini = bambini;
    }

    public int getCamere() {
        return camere;
    }

    public void setCamere(int camere) {
        this.camere = camere;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

}
