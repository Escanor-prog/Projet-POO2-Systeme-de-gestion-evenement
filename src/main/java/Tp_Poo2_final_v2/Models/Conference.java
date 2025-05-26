package Tp_Poo2_final_v2.Models;

import java.time.LocalDateTime;
import java.util.List;

public  class Conference extends Evenement{
    List <Intervenant> intervenants;
    String theme;
    public Conference() {

    }

    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax,List<Intervenant> intervenants,String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.intervenants = intervenants;
        this.theme = theme;
    }
    @Override
    protected void afficherDetailsSpecifiques() {
        System.out.println(" Theme : " + theme);
        System.out.println(" Intervenants : ");
        for (Intervenant i : intervenants) {
            System.out.println(" - " + i.getNom());
        }
    }
    @Override
    protected String getTypeEvenement() {
        return "Conférence";
    }
    // Getters utiles (facultatif)
    public List<Intervenant> getIntervenants() {
        return intervenants;
    }

    public void setIntervenants(List<Intervenant> intervenants) {
        this.intervenants = intervenants;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

}
