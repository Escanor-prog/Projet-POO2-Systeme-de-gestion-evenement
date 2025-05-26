import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.GestionEvenement;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import Tp_Poo2_final_v2.Exceptions.NombreMaxAtteint;
import Tp_Poo2_final_v2.Exceptions.ParticipantDejaExistant;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class TestTp {

    @Test
    void testInscriptionParticipant() throws Exception {
        Conference conf = new Conference("1", "Conf Test", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        Participant p = new Participant("p1", "Bob", "bob@mail.com");
        conf.ajouterParticipant(p);
        assertEquals(1, conf.getParticipants().size());
        assertEquals("Bob", conf.getParticipants().get(0).getNom());
        System.out.println("Test passé !");
    }

    @Test
    void testDoubleInscriptionParticipant() throws Exception {
        Conference conf = new Conference("2", "Conf Test", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        Participant p = new Participant("p2", "Charlie", "charlie@mail.com");
        conf.ajouterParticipant(p);
        // Tentative de double inscription
        Exception exception = assertThrows(Exception.class, () -> {
            conf.ajouterParticipant(p);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("déjà inscrit") || exception.getMessage().toLowerCase().contains("existant"));
        System.out.println("Test passé !");
    }

    @Test
    void testCapaciteMaxAtteinteException() throws Exception {
        Conference conf = new Conference("3", "Conf Test", LocalDateTime.now(), "Paris", 1,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        conf.ajouterParticipant(new Participant("p3", "Eve", "eve@mail.com"));
        Exception exception = assertThrows(Exception.class, () -> {
            conf.ajouterParticipant(new Participant("p4", "Mallory", "mallory@mail.com"));
        });
        assertTrue(exception.getMessage().toLowerCase().contains("capacité maximale"));
        System.out.println("Test passé !");
    }

    @Test
    void testAnnulationEtNotificationObserver() throws Exception {
        Conference conf = new Conference("4", "Conf Observer", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        AtomicBoolean notified = new AtomicBoolean(false);

        // Participant observer personnalisé pour le test
        Participant p = new Participant("p5", "Test", "test@mail.com") {
            @Override
            public void notifier(String message) {
                notified.set(true);
            }
        };
        conf.ajouterParticipant(p);
        conf.annuler();
        assertTrue(conf.isAnnule());
        assertTrue(notified.get());
        System.out.println("Test passé !");
    }

    @Test
    void testAnnulationDejaAnnulee() throws Exception {
        Conference conf = new Conference("5", "Conf Annulee", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        conf.annuler();
        assertTrue(conf.isAnnule());
        // Annulation à nouveau ne doit pas planter
        conf.annuler();
        assertTrue(conf.isAnnule());
        System.out.println("Test passé !");
    }

    @Test
    void testSerialisationDeserialisation() throws Exception {
        Conference conf = new Conference("6", "Conf Test", LocalDateTime.of(2025, 6, 15, 14, 30), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        Concert concert = new Concert("7", "Rock Night", LocalDateTime.of(2025, 7, 1, 20, 0), "Lyon", 1, "The Rockers", "Rock");
        List<Evenement> evenements = List.of(conf, concert);

        SerialisationJson.sauvegarderEvenements(evenements, "test_evenements.json");
        List<Evenement> charges = SerialisationJson.chargerEvenements("test_evenements.json");

        assertEquals(2, charges.size());
        assertEquals("Conf Test", charges.get(0).getNom());
        assertEquals("Rock Night", charges.get(1).getNom());

        // Nettoyage
        new File("test_evenements.json").delete();
        System.out.println("Test passé !");
    }

    @Test
    void testRechercheAvecStreamEtLambda() throws Exception {
        GestionEvenement gestion = GestionEvenement.getInstance();
        gestion.supprimerEvenement("10");
        gestion.supprimerEvenement("11");
        Conference conf = new Conference("10", "Conf Stream", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        Concert concert = new Concert("11", "Rock Stream", LocalDateTime.now(), "Lyon", 1, "The Rockers", "Rock");
        gestion.ajouterEvenement(conf);
        gestion.ajouterEvenement(concert);

        // Recherche Conference à Paris
        List<Evenement> confsParis = gestion.getEvenements().values().stream()
                .filter(e -> e instanceof Conference && e.getLieu().equalsIgnoreCase("Paris"))
                .toList();
        assertFalse(confsParis.isEmpty());

        // Recherche Concert à Lyon
        List<Evenement> concertsLyon = gestion.getEvenements().values().stream()
                .filter(e -> e instanceof Concert && e.getLieu().equalsIgnoreCase("Lyon"))
                .toList();
        assertFalse(concertsLyon.isEmpty());

        // Recherche qui ne trouve rien
        List<Evenement> concertsParis = gestion.getEvenements().values().stream()
                .filter(e -> e instanceof Concert && e.getLieu().equalsIgnoreCase("Paris"))
                .toList();
        assertTrue(concertsParis.isEmpty());
        System.out.println("Test passé !");
    }

    @Test
    void testSuppressionEvenementInexistant() {
        GestionEvenement gestion = GestionEvenement.getInstance();
        // Suppression d'un événement qui n'existe pas ne doit pas planter
        assertDoesNotThrow(() -> gestion.supprimerEvenement("9999"));
        System.out.println("Test passé !");
    }

    @Test
    void testParticipantSorcier() throws Exception {
        Conference conf = new Conference("12", "Conf Sorcier", LocalDateTime.now(), "Paris", 2,
                List.of(new Intervenant("Alice", "alice@mail.com", "Oratrice")), "Tech");
        Participant sorcier = new Participant("sorcier", "Sorcier", "sorcier@mail.com");
        conf.ajouterParticipant(sorcier);
        // Le sorcier tente de s'inscrire plusieurs fois
        Exception exception = assertThrows(Exception.class, () -> {
            conf.ajouterParticipant(sorcier);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("déjà inscrit") || exception.getMessage().toLowerCase().contains("existant"));
        System.out.println("Test passé !");
    }
}
