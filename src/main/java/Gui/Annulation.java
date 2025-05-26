package Gui;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.IOException;

// Boîte de dialogue pour annuler un événement et notifier les participants
public class Annulation extends JDialog {
    // ComboBox pour sélectionner l'événement à annuler
    private JComboBox<Evenement> eventBox;
    // Liste des événements chargés depuis le fichier JSON
    private List<Evenement> evenements;

    public Annulation(JFrame parent) {
        super(parent, "Annuler un événement", true);
        setLayout(new BorderLayout());

        // Charger les événements existants depuis le fichier JSON
        try {
            evenements = SerialisationJson.chargerEvenements("evenements.json");
        } catch (IOException ex) {
            evenements = new java.util.ArrayList<>();
        }

        // ComboBox pour choisir l'événement à annuler
        eventBox = new JComboBox<>(evenements.toArray(new Evenement[0]));
        add(eventBox, BorderLayout.NORTH);

        // Bouton pour annuler l'événement sélectionné
        JButton annulerBtn = new JButton("Annuler l'événement");
        annulerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        annulerBtn.setToolTipText("Annuler l'événement sélectionné et notifier les participants");
        annulerBtn.addActionListener(e -> {
            Evenement evt = (Evenement) eventBox.getSelectedItem();
            // Vérifie qu'un événement est sélectionné
            if (evt == null) {
                JOptionPane.showMessageDialog(this, "Aucun événement sélectionné.");
                return;
            }
            // Vérifie si l'événement est déjà annulé
            if (evt.isAnnule()) {
                JOptionPane.showMessageDialog(this, "Cet événement est déjà annulé.");
                return;
            }
            // Annule l'événement et notifie les participants (Observer)
            evt.annuler();
            try {
                // Sauvegarde la liste des événements mise à jour
                SerialisationJson.sauvegarderEvenements(evenements, "evenements.json");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde !");
                return;
            }
            JOptionPane.showMessageDialog(this, "Événement annulé et participants notifiés !");
            dispose();
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(annulerBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
