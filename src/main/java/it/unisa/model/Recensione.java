package it.unisa.model;

import java.sql.Timestamp;

public class Recensione {
    private int id;
    private int idCamera;
    private String nomeUtente;
    private String emailUtente;
    private int stelle;
    private String commento;
    private Timestamp dataRec;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCamera() { return idCamera; }
    public void setIdCamera(int idCamera) { this.idCamera = idCamera; }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public String getEmailUtente() { return emailUtente; }
    public void setEmailUtente(String emailUtente) { this.emailUtente = emailUtente; }

    public int getStelle() { return stelle; }
    public void setStelle(int stelle) { this.stelle = stelle; }

    public String getCommento() { return commento; }
    public void setCommento(String commento) { this.commento = commento; }

    public Timestamp getDataRec() { return dataRec; }
    public void setDataRec(Timestamp dataRec) { this.dataRec = dataRec; }
}