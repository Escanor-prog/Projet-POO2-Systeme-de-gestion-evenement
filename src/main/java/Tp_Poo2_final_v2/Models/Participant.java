package Tp_Poo2_final_v2.Models;

import Tp_Poo2_final_v2.Observers.ParticipantObserver;

public class Participant implements ParticipantObserver {

    private String id;
    private String nom;
    private String email;

    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public Participant() {
        this.id = "";
        this.nom = "";
        this.email = "";
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void notifier(String message) {
        System.out.println(" Notification pour " + this.nom + " : " + message);
    }
}
