

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SerialisationJsonTest {

    @Test // Test sérialisation et désérialisation JSON
    void testSerialisationDeserialisation() throws Exception {
        Conference conf = new Conference("c7", "Conf", LocalDateTime.now(), "Paris", 2, List.of(), "Tech");
        Concert concert = new Concert("co3", "Concert", LocalDateTime.now(), "Lyon", 5, "Artiste", "Rock");
        List<Evenement> evenements = List.of(conf, concert);
        SerialisationJson.sauvegarderEvenements(evenements, "test_evenements.json");
        List<Evenement> charges = SerialisationJson.chargerEvenements("test_evenements.json");
        assertEquals(2, charges.size());
        new File("test_evenements.json").delete();
    }
}
