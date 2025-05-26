package Tp_Poo2_final_v2.Models;

import java.time.LocalDateTime;

public class Concert extends Evenement{
    String artiste;
    String genre;
    public Concert() {
    }
    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genre) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genre = genre;
    }
    @Override
    protected void afficherDetailsSpecifiques() {
        System.out.println(" Artiste : " + artiste);
        System.out.println(" Genre : " + genre);
    }
    @Override
    protected String getTypeEvenement() {
        return "Concert";
    }
    // Getters utiles (facultatif)
    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
