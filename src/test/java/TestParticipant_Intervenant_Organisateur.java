

import Tp_Poo2_final_v2.Models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestParticipant_Intervenant_Organisateur {

    @Test // Test des getters/setters de Participant
    void testParticipantGettersSetters() {
        Participant p = new Participant("p1", "Alice", "alice@mail.com");
        assertEquals("p1", p.getId());
        assertEquals("Alice", p.getNom());
        assertEquals("alice@mail.com", p.getEmail());
        p.setNom("Alicia");
        assertEquals("Alicia", p.getNom());
    }

    @Test // Test de la notification (Observer)
    void testNotification() {
        Participant p = new Participant("p2", "Bob", "bob@mail.com");
        // Doit afficher un message dans la console
        p.notifier("Test notification");
    }

    @Test // Test Intervenant
    void testIntervenant() {
        Intervenant i = new Intervenant("Bob", "bob@mail.com", "Orateur");
        assertEquals("Orateur", i.getRole());
        i.setRole("Conférencier");
        assertEquals("Conférencier", i.getRole());
    }

    @Test // Test Organisateur
    void testOrganisateur() {
        Organisateur org = new Organisateur("o1", "Diane", "diane@mail.com");
        Conference conf = new Conference("c1", "Conf", java.time.LocalDateTime.now(), "Paris", 10, java.util.List.of(), "Tech");
        org.ajouterEvenementOrganise(conf);
        assertEquals(1, org.getEvenementsOrganises().size());
    }
}
