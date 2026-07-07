package it.unisa.model.dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unisa.model.Prenotazione;

public class PrenotazioneDAO {

	
	public void salvaPrenotazione(Prenotazione p, int idUtente, String nomeUtente, String cognomeUtente) {
        String sql = "INSERT INTO prenotazioni (id_utente, nome_utente, cognome_utente, nome_bnb, citta, checkin, checkout, adulti, bambini, camere, totale, immagine_bnb) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUtente);
            stmt.setString(2, nomeUtente);
            stmt.setString(3, cognomeUtente);
            stmt.setString(4, p.getNomeBnb());
            stmt.setString(5, p.getCitta());
            stmt.setDate(6, Date.valueOf(p.getCheckin()));
            stmt.setDate(7, Date.valueOf(p.getCheckout()));
            stmt.setInt(8, p.getAdulti());
            stmt.setInt(9, p.getBambini());
            stmt.setInt(10, p.getCamere());
            stmt.setDouble(11, p.getPrezzoTotale());
            stmt.setString(12, p.getImmagineBnb());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio prenotazione", e);
        }
    }

	public Prenotazione getPrenotazioneByUtente(int idUtente) {
	    String sql = "SELECT * FROM prenotazioni WHERE id_utente = ? ORDER BY id DESC LIMIT 1";

	    try (Connection con = DBManager.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setInt(1, idUtente);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            Prenotazione p = new Prenotazione();
	            p.setNomeBnb(rs.getString("nome_bnb"));
	            p.setCitta(rs.getString("citta"));
	            p.setCheckin(rs.getDate("checkin").toLocalDate());
	            p.setCheckout(rs.getDate("checkout").toLocalDate());
	            p.setAdulti(rs.getInt("adulti"));
	            p.setBambini(rs.getInt("bambini"));
	            p.setCamere(rs.getInt("camere"));
	            p.setPrezzoTotale(rs.getDouble("totale"));
	            p.setImmagineBnb(rs.getString("immagine_bnb"));
	            p.setId(rs.getInt("id")); // fondamentale per la cancellazione!

	            // Nuovi campi: nome e cognome utente
	            p.setNomeUtente(rs.getString("nome_utente"));
	            p.setCognomeUtente(rs.getString("cognome_utente"));

	            return p;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	 public void cancellaPrenotazioneById(int idPrenotazione) {
	        String sql = "DELETE FROM prenotazioni WHERE id = ?";

	        try (Connection con = DBManager.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, idPrenotazione);
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            throw new RuntimeException("Errore durante la cancellazione della prenotazione con id " + idPrenotazione, e);
	        }
	    }
	 
	 
	 public List<Prenotazione> findAll() {
		    List<Prenotazione> lista = new ArrayList<>();
		    String sql = "SELECT * FROM prenotazioni ORDER BY id DESC";
		    try (Connection con = DBManager.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {
		        while (rs.next()) {
		            Prenotazione p = new Prenotazione();
		            p.setId(rs.getInt("id"));
		            p.setNomeUtente(rs.getString("nome_utente"));
		            p.setCognomeUtente(rs.getString("cognome_utente"));
		            p.setNomeBnb(rs.getString("nome_bnb"));
		            p.setCitta(rs.getString("citta"));
		            p.setCheckin(rs.getDate("checkin").toLocalDate());
		            p.setCheckout(rs.getDate("checkout").toLocalDate());
		            p.setAdulti(rs.getInt("adulti"));
		            p.setBambini(rs.getInt("bambini"));
		            p.setCamere(rs.getInt("camere"));
		            p.setPrezzoTotale(rs.getDouble("totale"));
		            p.setImmagineBnb(rs.getString("immagine_bnb"));
		            lista.add(p);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return lista;
		}
	 
	 public List<Prenotazione> findByUtente(int idUtente) {
		    List<Prenotazione> lista = new ArrayList<>();
		    String sql = "SELECT * FROM prenotazioni WHERE id_utente = ? ORDER BY id DESC";
		    try (Connection con = DBManager.getConnection();
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setInt(1, idUtente);
		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		                Prenotazione p = new Prenotazione();
		                p.setId(rs.getInt("id"));
		                p.setNomeUtente(rs.getString("nome_utente"));
		                p.setCognomeUtente(rs.getString("cognome_utente"));
		                p.setNomeBnb(rs.getString("nome_bnb"));
		                p.setCitta(rs.getString("citta"));
		                p.setCheckin(rs.getDate("checkin").toLocalDate());
		                p.setCheckout(rs.getDate("checkout").toLocalDate());
		                p.setAdulti(rs.getInt("adulti"));
		                p.setBambini(rs.getInt("bambini"));
		                p.setCamere(rs.getInt("camere"));
		                p.setPrezzoTotale(rs.getDouble("totale"));
		                p.setImmagineBnb(rs.getString("immagine_bnb"));
		                lista.add(p);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return lista;
		}

		// Cancellazione sicura: elimina solo se la prenotazione appartiene davvero
		// all'utente loggato, così nessuno può cancellare prenotazioni altrui
		// manomettendo l'id nel form.
		public void cancellaPrenotazioneByIdEUtente(int idPrenotazione, int idUtente) {
		    String sql = "DELETE FROM prenotazioni WHERE id = ? AND id_utente = ?";
		    try (Connection con = DBManager.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {
		        ps.setInt(1, idPrenotazione);
		        ps.setInt(2, idUtente);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        throw new RuntimeException("Errore durante la cancellazione della prenotazione con id " + idPrenotazione, e);
		    }
		}
}
