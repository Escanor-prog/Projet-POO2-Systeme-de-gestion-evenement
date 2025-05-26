package Tp_Poo2_final_v2.Services;

import Tp_Poo2_final_v2.Exceptions.ParticipantDejaExistant;
import Tp_Poo2_final_v2.Models.Concert;
import Tp_Poo2_final_v2.Models.Evenement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionEvenement {

    //  Singleton
    private static GestionEvenement instance;

    //  Données internes
    private Map<String, Evenement> evenements;

    //  Constructeur privé
    private GestionEvenement() {
        this.evenements = new HashMap<>();
    }

    //  Point d’accès global
    public static GestionEvenement getInstance() {
        if (instance == null) {
            instance = new GestionEvenement();
        }
        return instance;
    }

    //  Méthodes de gestion
    public Map<String, Evenement> getEvenements() {
        return Collections.unmodifiableMap(evenements); // lecture seule
    }

    public void chargerDepuisFichier(Map<String, Evenement> nouvellesDonnees) {
        evenements.clear();
        evenements.putAll(nouvellesDonnees);
    }

    public Evenement getEvenement(String id) {
        return evenements.get(id);
    }

    public void ajouterEvenement(Evenement evenement) throws Exception {
        if(evenements.containsKey(evenement.getId())){
            throw new ParticipantDejaExistant("L'ID de l'événement est déjà utilisé.");
        }
        evenements.put(evenement.getId(), evenement);
        System.out.println(" Événement ajouté : " + evenement.getNom());
    }

    public void supprimerEvenement(String id) {
        evenements.remove(id);
    }

    public void modifierEvenement(Evenement evenement) {
        evenements.replace(evenement.getId(), evenement);
    }

    public void rechercherEvenementParNom(String nom) {
        boolean trouve = false;
        for (Evenement e : evenements.values()) {
            if (e.getNom().equals(nom)) {
                System.out.println("Evenement trouvé :\n");
                e.afficherDetails();
                trouve = true;

            }

        }
        if(!trouve){
            System.out.println(" Aucun événement trouvé avec le nom : " + nom);
        }
    }

    public void rechercherEvenementParId(String id) {
        Evenement e = evenements.get(id);
        if (e != null) {
            System.out.println("Evenement trouvé :\n");
            e.afficherDetails();
        } else {
            System.out.println(" Aucun événement trouvé avec l'ID : " + id);
        }
    }
    public void afficherTousLesEvenements() {
        if (evenements.isEmpty()) {
            System.out.println(" Aucun événement enregistré.");
            return;
        }
        System.out.println(" Liste des événements :");
        for (Evenement e : evenements.values()) {
            System.out.println("🔹----------------------------");
            e.afficherDetails();
        }
    }

    public void afficherConcertsParis() {
        System.out.println("Concerts à Paris :");
        evenements.values().stream()
            .filter(e -> e instanceof Concert && e.getLieu().equalsIgnoreCase("Paris"))
            .forEach(Evenement::afficherDetails);
    }

    public void rechercherEvenementsParTypeEtVille(Class<? extends Evenement> type, String ville) {
        List<Evenement> resultats = evenements.values().stream()
            .filter(e -> type.isInstance(e) && e.getLieu().equalsIgnoreCase(ville))
            .toList();

        if (resultats.isEmpty()) {
            System.out.println("Aucun événement de type " + type.getSimpleName() + " trouvé à " + ville + ".");
        } else {
            System.out.println("Événements trouvés :");
            resultats.forEach(Evenement::afficherDetails);
        }
    }
}
