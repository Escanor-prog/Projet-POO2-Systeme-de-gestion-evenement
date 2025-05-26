package Gui;

// Importation des modèles et services nécessaires
import Tp_Poo2_final_v2.Models.Evenement;
import Tp_Poo2_final_v2.Models.Concert;
import Tp_Poo2_final_v2.Models.Conference;
import Tp_Poo2_final_v2.Models.Intervenant;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import java.time.LocalDateTime;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Boîte de dialogue pour ajouter un événement (hérite de JDialog)
public class AddEventDialog extends JDialog {
    // Champs de saisie pour les informations de base
    private JTextField idField, nomField, lieuField, capaciteField;
    // ComboBox pour la date et l'heure
    private JComboBox<Integer> jourBox, moisBox, anneeBox, heureBox, minuteBox;
    // ComboBox pour choisir le type d'événement
    private JComboBox<String> typeBox;
    // Champs spécifiques selon le type d'événement
    private JTextField artisteField, genreField, themeField, intervenantNomField, intervenantEmailField, intervenantRoleField;
    // Modèle pour la liste des intervenants (affichage dynamique)
    private DefaultListModel<String> intervenantListModel;
    private DefaultListModel<String> organisateurListModel;
    private JTextField organisateurNomField, organisateurEmailField;
    // Liste statique pour stocker tous les événements (persistés en JSON)
    private static java.util.List<Evenement> evenements = new java.util.ArrayList<>();

    // Constructeur de la boîte de dialogue
    public AddEventDialog(JFrame parent) {
        super(parent, "Ajouter un événement", true); // Titre et modalité
        setSize(500, 500); // Taille initiale (sera ajustée avec pack())
        setLocationRelativeTo(parent); // Centre la fenêtre
        setLayout(new BorderLayout());

        // Panel principal du formulaire, vertical
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // --- Champs communs ---
        // ID
        form.add(new JLabel("<html><b>ID :</b></html>"));
        idField = new JTextField();
        idField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(idField);

        // Nom
        form.add(new JLabel("<html><b>Nom :</b></html>"));
        nomField = new JTextField();
        nomField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(nomField);

        // Lieu
        form.add(new JLabel("<html><b>Lieu :</b></html>"));
        lieuField = new JTextField();
        lieuField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(lieuField);

        // Capacité
        form.add(new JLabel("<html><b>Capacité max :</b></html>"));
        capaciteField = new JTextField();
        capaciteField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        form.add(capaciteField);

        // --- Sélection de la date et de l'heure ---
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDate.add(new JLabel("Date :"));
        jourBox = new JComboBox<>();
        moisBox = new JComboBox<>();
        anneeBox = new JComboBox<>();
        heureBox = new JComboBox<>();
        minuteBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) jourBox.addItem(i);
        for (int i = 1; i <= 12; i++) moisBox.addItem(i);
        for (int i = 2024; i <= 2030; i++) anneeBox.addItem(i);
        for (int i = 0; i < 24; i++) heureBox.addItem(i);
        for (int i = 0; i < 60; i+=5) minuteBox.addItem(i);
        panelDate.add(jourBox); panelDate.add(new JLabel("/"));
        panelDate.add(moisBox); panelDate.add(new JLabel("/"));
        panelDate.add(anneeBox); panelDate.add(new JLabel(" à "));
        panelDate.add(heureBox); panelDate.add(new JLabel("h"));
        panelDate.add(minuteBox); panelDate.add(new JLabel("min"));
        form.add(panelDate);

        // --- Type d'événement ---
        JPanel panelType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelType.add(new JLabel("Type :"));
        typeBox = new JComboBox<>(new String[]{"Concert", "Conférence"});
        panelType.add(typeBox);
        form.add(panelType);

        // --- Champs spécifiques Concert ---
        JPanel panelArtiste = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelArtiste.add(new JLabel("Artiste :"));
        artisteField = new JTextField(15);
        panelArtiste.add(artisteField);
        form.add(panelArtiste);

        JPanel panelGenre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGenre.add(new JLabel("Genre :"));
        genreField = new JTextField(15);
        panelGenre.add(genreField);
        form.add(panelGenre);

        // --- Champs spécifiques Conférence ---
        JPanel panelTheme = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTheme.add(new JLabel("Thème :"));
        themeField = new JTextField(15);
        panelTheme.add(themeField);
        form.add(panelTheme);

        // --- Gestion dynamique des intervenants ---
        form.add(new JLabel("Intervenants :"));
        JPanel intervenantPanel = new JPanel(new BorderLayout());
        intervenantListModel = new DefaultListModel<>();
        JList<String> intervenantList = new JList<>(intervenantListModel);
        intervenantPanel.add(new JScrollPane(intervenantList), BorderLayout.CENTER);

        // Panel pour ajouter un intervenant
        JPanel addIntervenantPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        intervenantNomField = new JTextField(7);
        intervenantEmailField = new JTextField(10);
        intervenantRoleField = new JTextField(7);
        JButton addIntervenantBtn = new JButton("+");
        addIntervenantBtn.setToolTipText("Ajouter un intervenant");
        addIntervenantBtn.addActionListener(e -> {
            // Ajoute un intervenant à la liste si tous les champs sont remplis
            String nom = intervenantNomField.getText().trim();
            String email = intervenantEmailField.getText().trim();
            String role = intervenantRoleField.getText().trim();
            if (!nom.isEmpty() && !email.isEmpty() && !role.isEmpty()) {
                intervenantListModel.addElement(nom + " (" + email + ", " + role + ")");
                intervenantNomField.setText("");
                intervenantEmailField.setText("");
                intervenantRoleField.setText("");
            }
        });
        addIntervenantPanel.add(new JLabel("Nom:"));
        addIntervenantPanel.add(intervenantNomField);
        addIntervenantPanel.add(new JLabel("Email:"));
        addIntervenantPanel.add(intervenantEmailField);
        addIntervenantPanel.add(new JLabel("Rôle:"));
        addIntervenantPanel.add(intervenantRoleField);
        addIntervenantPanel.add(addIntervenantBtn);
        intervenantPanel.add(addIntervenantPanel, BorderLayout.SOUTH);
        form.add(intervenantPanel);

        // --- Gestion dynamique des organisateurs ---
        form.add(new JLabel("Organisateurs :"));
        JPanel organisateurPanel = new JPanel(new BorderLayout());
        organisateurListModel = new DefaultListModel<>();
        JList<String> organisateurList = new JList<>(organisateurListModel);
        organisateurPanel.add(new JScrollPane(organisateurList), BorderLayout.CENTER);

        JPanel addOrganisateurPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        organisateurNomField = new JTextField(7);
        organisateurEmailField = new JTextField(10);
        JButton addOrganisateurBtn = new JButton("+");
        addOrganisateurBtn.setToolTipText("Ajouter un organisateur");
        addOrganisateurBtn.addActionListener(e -> {
            String nom = organisateurNomField.getText().trim();
            String email = organisateurEmailField.getText().trim();
            if (!nom.isEmpty() && !email.isEmpty()) {
                organisateurListModel.addElement(nom + " (" + email + ")");
                organisateurNomField.setText("");
                organisateurEmailField.setText("");
            }
        });
        addOrganisateurPanel.add(new JLabel("Nom:"));
        addOrganisateurPanel.add(organisateurNomField);
        addOrganisateurPanel.add(new JLabel("Email:"));
        addOrganisateurPanel.add(organisateurEmailField);
        addOrganisateurPanel.add(addOrganisateurBtn);
        organisateurPanel.add(addOrganisateurPanel, BorderLayout.SOUTH);
        form.add(organisateurPanel);

        // Affichage conditionnel des champs selon le type sélectionné
        typeBox.addActionListener(e -> updateFields());

        // Ajoute le formulaire dans un JScrollPane pour gérer le débordement
        add(new JScrollPane(form), BorderLayout.CENTER);

        // --- Bouton de validation ---
        JButton validerBtn = new JButton("Valider");
        validerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        validerBtn.setToolTipText("Valider l'ajout de l'événement");
        validerBtn.addActionListener(e -> {
            // Récupération des valeurs saisies
            String id = idField.getText().trim();
            String nom = nomField.getText();
            String lieu = lieuField.getText();
            int capacite = Integer.parseInt(capaciteField.getText());
            String type = (String) typeBox.getSelectedItem();

            // Vérification de l'unicité de l'ID
            boolean existe = evenements.stream().anyMatch(ev -> ev.getId().equals(id));
            if (existe) {
                JOptionPane.showMessageDialog(this, "Un événement avec cet ID existe déjà !");
                return;
            }

            // Construction de la date et de l'heure
            int jour = (int) jourBox.getSelectedItem();
            int mois = (int) moisBox.getSelectedItem();
            int annee = (int) anneeBox.getSelectedItem();
            int heure = (int) heureBox.getSelectedItem();
            int minute = (int) minuteBox.getSelectedItem();
            LocalDateTime date = LocalDateTime.of(annee, mois, jour, heure, minute);

            Evenement evt;
            if (type.equals("Concert")) {
                // Création d'un Concert
                String artiste = artisteField.getText();
                String genre = genreField.getText();
                evt = new Concert(id, nom, date, lieu, capacite, artiste, genre);
            } else {
                // Création d'une Conférence avec la liste des intervenants
                String theme = themeField.getText();
                ArrayList<Intervenant> intervenants = new ArrayList<>();
                for (int i = 0; i < intervenantListModel.size(); i++) {
                    String entry = intervenantListModel.get(i); // Format: nom (email, role)
                    String[] parts = entry.split("\\(");
                    String nomI = parts[0].trim();
                    String[] infos = parts[1].replace(")", "").split(",");
                    String email = infos[0].trim();
                    String role = infos[1].trim();
                    intervenants.add(new Intervenant(nomI, email, role));
                }
                evt = new Conference(id, nom, date, lieu, capacite, intervenants, theme);
            }

            // Sérialisation JSON via la classe utilitaire (Jackson)
            evenements.add(evt);
            try {
                SerialisationJson.sauvegarderEvenements(evenements, "evenements.json");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Événement ajouté et sauvegardé !");
            dispose(); // Ferme la boîte de dialogue
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(validerBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Chargement des événements existants depuis le fichier JSON
        try {
            evenements = SerialisationJson.chargerEvenements("evenements.json");
        } catch (IOException ex) {
            evenements = new java.util.ArrayList<>();
        }

        updateFields(); // Met à jour l'affichage des champs selon le type
        pack(); // Ajuste la taille de la fenêtre au contenu
        setLocationRelativeTo(parent);
        // PAS de setSize(1000, 800) ou autre très grand
    }

    // Méthode pour activer/désactiver les champs selon le type d'événement
    private void updateFields() {
        boolean isConcert = typeBox.getSelectedItem().equals("Concert");
        artisteField.setEnabled(isConcert);
        genreField.setEnabled(isConcert);
        themeField.setEnabled(!isConcert);
        intervenantNomField.setEnabled(!isConcert);
        intervenantEmailField.setEnabled(!isConcert);
        intervenantRoleField.setEnabled(!isConcert);
    }
}
