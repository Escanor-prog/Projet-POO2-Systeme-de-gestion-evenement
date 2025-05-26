package Gui;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.IOException;

// Boîte de dialogue pour afficher, modifier ou supprimer les détails d'un événement
public class AfficherDetails extends JDialog {
    // ComboBox pour sélectionner l'événement à afficher
    private JComboBox<Evenement> eventBox;
    // Zone de texte pour afficher les détails de l'événement
    private JTextArea detailsArea;
    // Liste des événements chargés depuis le fichier JSON
    private List<Evenement> evenements;

    public AfficherDetails(JFrame parent) {
        super(parent, "Détails d'un événement", true);
        setLayout(new BorderLayout());

        // Charger les événements existants depuis le fichier JSON
        try {
            evenements = SerialisationJson.chargerEvenements("evenements.json");
        } catch (IOException ex) {
            evenements = new java.util.ArrayList<>();
        }

        // ComboBox pour choisir l'événement à afficher
        eventBox = new JComboBox<>(evenements.toArray(new Evenement[0]));
        eventBox.addActionListener(e -> afficherDetails());
        add(eventBox, BorderLayout.NORTH);

        // Zone de texte non éditable pour afficher les détails
        detailsArea = new JTextArea(15, 40);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Segoe UI", Font.BOLD, 15));
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // Panneau pour les boutons Modifier et Supprimer
        JPanel btnPanel = new JPanel();

        // Bouton pour modifier l'événement sélectionné
        JButton modifierBtn = new JButton("\u270F\uFE0F  Modifier");
        modifierBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        modifierBtn.setToolTipText("Modifier cet événement");

        // Bouton pour supprimer l'événement sélectionné
        JButton supprimerBtn = new JButton("\u274C  Supprimer");
        supprimerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        supprimerBtn.setToolTipText("Supprimer cet événement");

        // Action de modification : ouvre la boîte de dialogue de modification, recharge la liste après modification
        modifierBtn.addActionListener(e -> {
            Evenement evt = (Evenement) eventBox.getSelectedItem();
            if (evt == null) return;
            new ModifierEvenementDialog((JFrame) getParent(), evt).setVisible(true);
            // Recharge la liste après modification
            try {
                evenements = SerialisationJson.chargerEvenements("evenements.json");
                eventBox.setModel(new DefaultComboBoxModel<>(evenements.toArray(new Evenement[0])));
                afficherDetails();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur de rechargement !");
            }
        });

        // Action de suppression : demande confirmation, supprime l'événement, sauvegarde et met à jour la liste
        supprimerBtn.addActionListener(e -> {
            Evenement evt = (Evenement) eventBox.getSelectedItem();
            if (evt == null) return;
            int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet événement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                evenements.remove(evt);
                try {
                    SerialisationJson.sauvegarderEvenements(evenements, "evenements.json");
                    eventBox.setModel(new DefaultComboBoxModel<>(evenements.toArray(new Evenement[0])));
                    afficherDetails();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
                }
            }
        });

        btnPanel.add(modifierBtn);
        btnPanel.add(supprimerBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Affiche les détails du premier événement si la liste n'est pas vide
        if (eventBox.getItemCount() > 0) {
            eventBox.setSelectedIndex(0);
            afficherDetails();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    // Affiche les détails de l'événement sélectionné dans la zone de texte
    private void afficherDetails() {
        Evenement evt = (Evenement) eventBox.getSelectedItem();
        if (evt == null) {
            detailsArea.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nom : ").append(evt.getNom()).append("\n");
        sb.append("Date : ").append(evt.getDate()).append("\n");
        sb.append("Lieu : ").append(evt.getLieu()).append("\n");
        sb.append("Capacité : ").append(evt.getParticipants().size())
          .append(" / ").append(evt.getCapaciteMax()).append("\n");
        sb.append("Annulé : ").append(evt.isAnnule() ? "Oui" : "Non").append("\n");
        sb.append("Participants :\n");
        // Affiche la liste des participants avec leur nom et email
        for (Tp_Poo2_final_v2.Models.Participant p : evt.getParticipants()) {
            sb.append(" - ").append(p.getNom()).append(" (").append(p.getEmail()).append(")\n");
        }
        detailsArea.setText(sb.toString());
    }
}
