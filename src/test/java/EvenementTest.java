import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Exceptions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EvenementTest {

    @Test // Test ajout de participant et capacité max
    void testAjouterParticipantEtCapacite() throws Exception {
        Conference conf = new Conference("c2", "Conf", LocalDateTime.now(), "Paris", 1, List.of(), "Tech");
        Participant p = new Participant("p2", "Eve", "eve@mail.com");
        conf.ajouterParticipant(p);
        assertEquals(1, conf.getParticipants().size());
        // Test dépassement capacité
        Exception ex = assertThrows(NombreMaxAtteint.class, () -> {
            conf.ajouterParticipant(new Participant("p3", "Mallory", "mallory@mail.com"));
        });
        assertTrue(ex.getMessage().toLowerCase().contains("capacité"));
    }

    @Test // Test double inscription
    void testDoubleInscription() throws Exception {
        Conference conf = new Conference("c3", "Conf", LocalDateTime.now(), "Paris", 2, List.of(), "Tech");
        Participant p = new Participant("p4", "Bob", "bob@mail.com");
        conf.ajouterParticipant(p);
        Exception ex = assertThrows(ParticipantDejaExistant.class, () -> {
            conf.ajouterParticipant(p);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("déjà inscrit"));
    }

    @Test // Test annulation et notification
    void testAnnulation() {
        Conference conf = new Conference("c4", "Conf", LocalDateTime.now(), "Paris", 2, List.of(), "Tech");
        conf.annuler();
        assertTrue(conf.isAnnule());
    }

    @Test // Test héritage Concert
    void testConcert() {
        Concert concert = new Concert("co1", "Concert", LocalDateTime.now(), "Lyon", 5, "Artiste", "Rock");
        assertEquals("Artiste", concert.getArtiste());
        assertEquals("Rock", concert.getGenre());
    }

    @Test
    void testSuppressionParticipantNonInscrit() throws Exception {
        Conference conf = new Conference("c8", "Conf", LocalDateTime.now(), "Paris", 2, List.of(), "Tech");
        Participant p = new Participant("p9", "NonInscrit", "noninscrit@mail.com");
        // Suppression d'un participant non inscrit ne doit pas planter
        assertDoesNotThrow(() -> conf.getParticipants().remove(p));
    }
}
