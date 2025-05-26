
import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.GestionEvenement;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GestionEvenementTest {

    @Test // Test singleton
    void testSingleton() {
        GestionEvenement g1 = GestionEvenement.getInstance();
        GestionEvenement g2 = GestionEvenement.getInstance();
        assertSame(g1, g2);
    }

    @Test // Test ajout, suppression, recherche
    void testAjoutSuppressionRecherche() throws Exception {
        GestionEvenement gestion = GestionEvenement.getInstance();
        Conference conf = new Conference("c5", "Conf", LocalDateTime.now(), "Paris", 10, List.of(), "Tech");
        gestion.ajouterEvenement(conf);
        assertNotNull(gestion.getEvenements().get("c5"));
        gestion.supprimerEvenement("c5");
        assertNull(gestion.getEvenements().get("c5"));
    }

    @Test // Test recherche avec stream/lambda
    void testRechercheStream() throws Exception {
        GestionEvenement gestion = GestionEvenement.getInstance();
        Conference conf = new Conference("c6", "Conf", LocalDateTime.now(), "Paris", 10, List.of(), "Tech");
        Concert concert = new Concert("co2", "Concert", LocalDateTime.now(), "Lyon", 5, "Artiste", "Rock");
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
    }
}
