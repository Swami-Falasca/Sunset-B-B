package it.unisa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int idCliente;
    private LocalDateTime dataOrdine;
    private double totale;
    private String struttura;
    private String citta;
    private LocalDate checkin;
    private LocalDate checkout;
    private int persone;
    private String stato;
    
    
    // Costruttore vuoto
    public Ordine() {}

    // Costruttore con parametri (opzionale)
    public Ordine(int id, int idCliente, LocalDateTime dataOrdine, double totale, String struttura, String citta, LocalDate checkin, LocalDate checkout, int persone, String stato) {
        this.id = id;
        this.idCliente = idCliente;
        this.dataOrdine = dataOrdine;
        this.totale = totale;
        this.struttura = struttura;
        this.citta = citta;
        this.checkin = checkin;
        this.checkout = checkout;
        this.persone = persone;
        this.stato = stato;
    }

 // Getters
    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public LocalDateTime getDataOrdine() {
        return dataOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public String getStruttura() {
        return struttura;
    }

    public String getCitta() {
        return citta;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public int getPersone() {
        return persone;
    }

    public String getStato() {
        return stato;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setDataOrdine(LocalDateTime dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public void setStruttura(String struttura) {
        this.struttura = struttura;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public void setPersone(int persone) {
        this.persone = persone;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
