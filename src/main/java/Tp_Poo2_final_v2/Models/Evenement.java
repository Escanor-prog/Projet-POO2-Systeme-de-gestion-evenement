package Tp_Poo2_final_v2.Models;

import Tp_Poo2_final_v2.Exceptions.NombreMaxAtteint;
import Tp_Poo2_final_v2.Exceptions.ParticipantDejaExistant;
import Tp_Poo2_final_v2.Observers.EvenementObservable;
import Tp_Poo2_final_v2.Observers.ParticipantObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.concurrent.CompletableFuture;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Conference.class, name = "conference"),
    @JsonSubTypes.Type(value = Concert.class, name = "concert")
})
public abstract class Evenement implements EvenementObservable {

    @JsonIgnore
    private List<ParticipantObserver> observateurs = new ArrayList<>();

    protected String id;
    protected String nom;
    protected LocalDateTime date;
    protected String lieu;
    protected int capaciteMax;
    protected List<Participant> participants;
    protected boolean annule; // ou estAnnule

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.annule = false;
    }

    public Evenement() {
        // Constructeur vide pour Jackson
    }

    public void ajouterParticipant(Participant p) throws Exception {
        for (Participant part : participants) {
            if (part.getId().equals(p.getId())) {
                throw new ParticipantDejaExistant("Participant déjà inscrit !");
            }
        }
        if (participants.size() >= capaciteMax) {
            throw new NombreMaxAtteint("Capacité maximale atteinte !");
        }
        participants.add(p);

        AjouterObserver(p); //  important pour le pattern Observer
        System.out.println(  p.getNom() + " inscrit à l'événement " + nom);
    }


    @Override
    public void annuler() {
        this.annule = true;
        notifierObserver(" L'événement \"" + nom + "\" a été annulé.");
    }

    public void annulerAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000); // Simule un délai de 2 secondes
            } catch (InterruptedException ignored) {}
            this.annule = true;
            notifierObserver("L'événement \"" + nom + "\" a été annulé (notification asynchrone).");
        });
    }

    public void afficherDetails() {
        System.out.println(" Événement : " + nom + " [" + getTypeEvenement() + "]");
        System.out.println(" Date : " + date);
        System.out.println(" Lieu : " + lieu);
        System.out.println(" Capacité maximale : " + capaciteMax);
        System.out.println(" Participants inscrits : " + participants.size());
        System.out.println(" Annulé : " + annule);
        afficherDetailsSpecifiques();
    }

    protected abstract void afficherDetailsSpecifiques();
    protected abstract String getTypeEvenement();

    // Implémentation du pattern Observer
    @Override
    public void AjouterObserver(ParticipantObserver observer) {
        if (!observateurs.contains(observer)) {
            observateurs.add(observer);
        }
    }

    @Override
    public void supprimerObserver(ParticipantObserver observer) {
        observateurs.remove(observer);
    }

    @Override
    public void notifierObserver(String message) {
        for (ParticipantObserver obs : observateurs) {
            obs.notifier(message);
        }
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public boolean isAnnule() {
        return annule;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public void setId(String id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public void setParticipants(List<Participant> participants) { this.participants = participants; }

    public int getCapaciteMax() {
        return capaciteMax;
    }
    public List<ParticipantObserver> getObservateurs() {
        return observateurs;
    }
}