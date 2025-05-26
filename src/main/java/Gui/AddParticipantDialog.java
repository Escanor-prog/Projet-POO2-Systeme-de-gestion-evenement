package Gui;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.io.IOException;

// Boîte de dialogue pour ajouter un participant à un événement
public class AddParticipantDialog extends JDialog {
    // ComboBox pour sélectionner l'événement
    private JComboBox<Evenement> eventBox;
    // Champs de saisie pour les infos du participant
    private JTextField idField, nomField, emailField;
    // Liste des événements chargés depuis le fichier JSON
    private List<Evenement> evenements;

    public AddParticipantDialog(JFrame parent) {
        super(parent, "Ajouter un participant", true);
        setLayout(new BorderLayout());

        // Charger les événements existants depuis le fichier JSON
        try {
            evenements = SerialisationJson.chargerEvenements("evenements.json");
        } catch (IOException ex) {
            evenements = new java.util.ArrayList<>();
        }

        // Création du formulaire de saisie
        JPanel form = new JPanel(new GridLayout(0,2,5,5));

        JLabel eventLabel = new JLabel("Événement :");
        eventLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(eventLabel);
        // ComboBox pour choisir l'événement
        eventBox = new JComboBox<>(evenements.toArray(new Evenement[0]));
        eventBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(eventBox);

        JLabel idLabel = new JLabel("ID participant :");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(idLabel);
        idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(idField);

        JLabel nomLabel = new JLabel("Nom :");
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(nomLabel);
        nomField = new JTextField();
        nomField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(nomField);

        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(emailLabel);
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(emailField);

        add(form, BorderLayout.CENTER); // Ajout du formulaire au centre de la boîte de dialogue

        // Bouton de validation
        JButton validerBtn = new JButton("Valider");
        validerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        validerBtn.setToolTipText("Valider l'ajout du participant");
        validerBtn.addActionListener(e -> {
            // Récupération des valeurs saisies
            Evenement evt = (Evenement) eventBox.getSelectedItem();
            String id = idField.getText().trim();
            String nom = nomField.getText().trim();
            String email = emailField.getText().trim();
            // Vérification des champs obligatoires
            if (evt == null || id.isEmpty() || nom.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }
            // Vérifie si l'événement est annulé
            if (evt.isAnnule()) {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter un participant à un événement annulé !");
                return;
            }
            // Vérifier si le participant est déjà inscrit à cet événement
            boolean deja = evt.getParticipants().stream().anyMatch(p -> p.getId().equals(id));
            if (deja) {
                JOptionPane.showMessageDialog(this, "Ce participant est déjà inscrit !");
                return;
            }
            try {
                // Création et ajout du participant
                Participant p = new Participant(id, nom, email);
                evt.ajouterParticipant(p);
                // Sauvegarde de la liste des événements mise à jour
                SerialisationJson.sauvegarderEvenements(evenements, "evenements.json");
                JOptionPane.showMessageDialog(this, "Participant ajouté !");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(validerBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
