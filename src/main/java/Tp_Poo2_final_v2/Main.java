package Tp_Poo2_final_v2;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.GestionEvenement;
import Tp_Poo2_final_v2.Services.SerialisationJson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dateConf = null;
        while (dateConf == null) {
            System.out.print("Entrez la date et l'heure de la conférence (ex: 2025-06-15 14:30) : ");
            String dateConfStr = scanner.nextLine();
            try {
                dateConf = LocalDateTime.parse(dateConfStr, formatter);
            } catch (Exception e) {
                System.out.println("Format invalide ou date impossible. Réessayez !");
            }
        }

        LocalDateTime dateConcert = null;
        while (dateConcert == null) {
            System.out.print("Entrez la date et l'heure du concert (ex: 2025-07-01 20:00) : ");
            String dateConcertStr = scanner.nextLine();
            try {
                dateConcert = LocalDateTime.parse(dateConcertStr, formatter);
            } catch (Exception e) {
                System.out.println("Format invalide ou date impossible. Réessayez !");
            }
        }

        Conference conf = new Conference(
            "1", "Conf Java", dateConf, "Paris", 100,
            Arrays.asList(new Intervenant("Alice", "alice@mail.com", "Oratrice")),
            "Programmation"
        );
        Concert concert = new Concert(
            "2", "Rock Night", dateConcert, "Lyon", 200,
            "The Rockers", "Rock"
        );

        List<Evenement> evenements = List.of(conf, concert);

        SerialisationJson.sauvegarderEvenements(evenements, "evenements2.json");
        System.out.println("Sérialisation réussie.");

        // Désérialisation
        List<Evenement> charges = SerialisationJson.chargerEvenements("evenements2.json");
        System.out.println("Désérialisation réussie :");
        GestionEvenement gestion = GestionEvenement.getInstance();
        for (Evenement e : charges) {
            e.afficherDetails();
            gestion.ajouterEvenement(e); // <-- Ajoute chaque événement dans la gestion
        }

        System.out.print("Quel type d'événement cherchez-vous ? (conference/concert) : ");
        String typeStr = scanner.nextLine().trim().toLowerCase();
        Class<? extends Evenement> typeRecherche = null;
        if (typeStr.equals("conference")) typeRecherche = Conference.class;
        else if (typeStr.equals("concert")) typeRecherche = Concert.class;
        else {
            System.out.println("Type inconnu.");
            return;
        }

        System.out.print("Dans quelle ville ? : ");
        String ville = scanner.nextLine().trim();

        gestion.rechercherEvenementsParTypeEtVille(typeRecherche, ville);

        // ...après avoir créé un événement et ajouté des participants/observers...
        System.out.println("Annulation asynchrone de la conférence...");
        conf.annulerAsync();
        System.out.println("Vous pouvez continuer à utiliser le programme pendant l'envoi de la notification.");
        Thread.sleep(3000); // Pour laisser le temps à la notification de s'afficher avant la fin du main
    }
}
