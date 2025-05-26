package Tp_Poo2_final_v2.Services;

import Tp_Poo2_final_v2.Models.Concert;
import Tp_Poo2_final_v2.Models.Conference;
import Tp_Poo2_final_v2.Models.Evenement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SerialisationJson {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        module.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static void sauvegarderEvenements(List<Evenement> evenements, String chemin) throws IOException {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Evenement e : evenements) {
            ObjectNode node = mapper.valueToTree(e);
            if (e instanceof Conference) {
                node.put("type", "conference");
            } else if (e instanceof Concert) {
                node.put("type", "concert");
            }
            arrayNode.add(node);
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(chemin), arrayNode);
    }

    public static List<Evenement> chargerEvenements(String chemin) throws IOException {
        return mapper.readValue(new File(chemin), new TypeReference<List<Evenement>>() {});
    }
}