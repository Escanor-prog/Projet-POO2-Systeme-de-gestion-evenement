package Gui;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

// Boîte de dialogue pour rechercher des événements selon différents critères
public class RechercheEvenementDialog extends JDialog {
    public RechercheEvenementDialog(JFrame parent) {
        super(parent, "Recherche d'événement", true);
        setLayout(new BorderLayout());

        // Champ de recherche pour le nom ou le lieu
        JTextField searchField = new JTextField(20);
        // ComboBox pour filtrer par type d'événement
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Tous", "Concert", "Conférence"});
        // Bouton pour lancer la recherche
        JButton searchBtn = new JButton("Rechercher");
        // Zone de texte pour afficher les résultats
        JTextArea resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);

        // Panneau supérieur contenant les champs de recherche
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Nom ou lieu :"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Type :"));
        topPanel.add(typeBox);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Action de recherche : filtre la liste selon le texte et le type sélectionné
        searchBtn.addActionListener(e -> {
            try {
                // Charge la liste des événements depuis le fichier JSON
                List<Evenement> evenements = SerialisationJson.chargerEvenements("evenements.json");
                String query = searchField.getText().trim().toLowerCase();
                String type = (String) typeBox.getSelectedItem();
                // Filtrage avec Stream selon le texte et le type
                List<Evenement> resultats = evenements.stream()
                    .filter(ev -> (query.isEmpty() ||
                                   ev.getNom().toLowerCase().contains(query) ||
                                   ev.getLieu().toLowerCase().contains(query)) &&
                                  (type.equals("Tous") ||
                                   (type.equals("Concert") && ev instanceof Concert) ||
                                   (type.equals("Conférence") && ev instanceof Conference)))
                    .collect(Collectors.toList());
                // Affichage des résultats
                StringBuilder sb = new StringBuilder();
                if (resultats.isEmpty()) {
                    sb.append("Aucun événement trouvé.");
                } else {
                    for (Evenement ev : resultats) {
                        sb.append(ev.getNom()).append(" - ").append(ev.getLieu())
                          .append(" (").append(ev.getDate()).append(")\n");
                    }
                }
                resultArea.setText(sb.toString());
            } catch (IOException ex) {
                resultArea.setText("Erreur de lecture du fichier !");
            }
        });

        // Ajuste la taille de la boîte de dialogue et la centre
        pack();
        setLocationRelativeTo(parent);
    }
}