package Tp_Poo2_final_v2.Models;

public class Intervenant {
    String nom;
    String email;
    String role;

    public Intervenant() {} // <-- Ajoute ce constructeur vide

    public Intervenant(String nom, String email, String role) {
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    // getters/setters (recommandé pour Jackson)
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
