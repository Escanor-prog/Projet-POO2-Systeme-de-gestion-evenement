package Gui;

import Tp_Poo2_final_v2.Models.*;
import Tp_Poo2_final_v2.Services.SerialisationJson;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ModifierEvenementDialog extends JDialog {
    private JTextField nomField, lieuField, capaciteField, artisteField, genreField, themeField;
    private JComboBox<Integer> jourBox, moisBox, anneeBox, heureBox, minuteBox;
    private DefaultListModel<String> intervenantListModel;
    private JTextField intervenantNomField, intervenantEmailField, intervenantRoleField;
    private Evenement evenement;
    private List<Evenement> evenements;

    public ModifierEvenementDialog(JFrame parent, Evenement evt) {
        super(parent, "Modifier l'événement", true);
        this.evenement = evt;

        // Charger la liste complète pour sauvegarde
        try {
            evenements = SerialisationJson.chargerEvenements("evenements.json");
        } catch (IOException ex) {
            evenements = new ArrayList<>();
        }

        setLayout(new BorderLayout());
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // Nom
        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNom.add(new JLabel("Nom :"));
        nomField = new JTextField(evt.getNom(), 15);
        panelNom.add(nomField);
        form.add(panelNom);

        // Lieu
        JPanel panelLieu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLieu.add(new JLabel("Lieu :"));
        lieuField = new JTextField(evt.getLieu(), 15);
        panelLieu.add(lieuField);
        form.add(panelLieu);

        // Capacité
        JPanel panelCapacite = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCapacite.add(new JLabel("Capacité max :"));
        capaciteField = new JTextField(String.valueOf(evt.getCapaciteMax()), 15);
        panelCapacite.add(capaciteField);
        form.add(panelCapacite);

        // Date
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
        for (int i = 0; i < 60; i += 5) minuteBox.addItem(i);
        LocalDateTime date = evt.getDate();
        jourBox.setSelectedItem(date.getDayOfMonth());
        moisBox.setSelectedItem(date.getMonthValue());
        anneeBox.setSelectedItem(date.getYear());
        heureBox.setSelectedItem(date.getHour());
        minuteBox.setSelectedItem(date.getMinute());
        panelDate.add(jourBox); panelDate.add(new JLabel("/"));
        panelDate.add(moisBox); panelDate.add(new JLabel("/"));
        panelDate.add(anneeBox); panelDate.add(new JLabel(" à "));
        panelDate.add(heureBox); panelDate.add(new JLabel("h"));
        panelDate.add(minuteBox); panelDate.add(new JLabel("min"));
        form.add(panelDate);

        // Champs spécifiques
        if (evt instanceof Concert concert) {
            JPanel panelArtiste = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelArtiste.add(new JLabel("Artiste :"));
            artisteField = new JTextField(concert.getArtiste(), 15);
            panelArtiste.add(artisteField);
            form.add(panelArtiste);

            JPanel panelGenre = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelGenre.add(new JLabel("Genre :"));
            genreField = new JTextField(concert.getGenre(), 15);
            panelGenre.add(genreField);
            form.add(panelGenre);
        } else if (evt instanceof Conference conf) {
            JPanel panelTheme = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelTheme.add(new JLabel("Thème :"));
            themeField = new JTextField(conf.getTheme(), 15);
            panelTheme.add(themeField);
            form.add(panelTheme);

            // Intervenants
            form.add(new JLabel("Intervenants :"));
            JPanel intervenantPanel = new JPanel(new BorderLayout());
            intervenantListModel = new DefaultListModel<>();
            for (Intervenant i : conf.getIntervenants()) {
                intervenantListModel.addElement(i.getNom() + " (" + i.getEmail() + ", " + i.getRole() + ")");
            }
            JList<String> intervenantList = new JList<>(intervenantListModel);
            intervenantPanel.add(new JScrollPane(intervenantList), BorderLayout.CENTER);

            JPanel addIntervenantPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
            intervenantNomField = new JTextField(7);
            intervenantEmailField = new JTextField(10);
            intervenantRoleField = new JTextField(7);
            JButton addIntervenantBtn = new JButton("+");
            addIntervenantBtn.setToolTipText("Ajouter un intervenant");
            addIntervenantBtn.addActionListener(e -> {
                String nomI = intervenantNomField.getText().trim();
                String emailI = intervenantEmailField.getText().trim();
                String roleI = intervenantRoleField.getText().trim();
                if (!nomI.isEmpty() && !emailI.isEmpty() && !roleI.isEmpty()) {
                    intervenantListModel.addElement(nomI + " (" + emailI + ", " + roleI + ")");
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
        }

        add(new JScrollPane(form), BorderLayout.CENTER);

        // Bouton de validation
        JButton validerBtn = new JButton("Enregistrer");
        validerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        validerBtn.setToolTipText("Enregistrer les modifications de l'événement");
        validerBtn.addActionListener(e -> {
            try {
                evenement.setNom(nomField.getText().trim());
                evenement.setLieu(lieuField.getText().trim());
                evenement.setCapaciteMax(Integer.parseInt(capaciteField.getText().trim()));
                LocalDateTime newDate = LocalDateTime.of(
                        (int) anneeBox.getSelectedItem(),
                        (int) moisBox.getSelectedItem(),
                        (int) jourBox.getSelectedItem(),
                        (int) heureBox.getSelectedItem(),
                        (int) minuteBox.getSelectedItem()
                );
                evenement.setDate(newDate);

                if (evenement instanceof Concert concert) {
                    concert.setArtiste(artisteField.getText().trim());
                    concert.setGenre(genreField.getText().trim());
                } else if (evenement instanceof Conference conf) {
                    conf.setTheme(themeField.getText().trim());
                    ArrayList<Intervenant> intervenants = new ArrayList<>();
                    for (int i = 0; i < intervenantListModel.size(); i++) {
                        String entry = intervenantListModel.get(i); // nom (email, role)
                        String[] parts = entry.split("\\(");
                        String nomI = parts[0].trim();
                        String[] infos = parts[1].replace(")", "").split(",");
                        String emailI = infos[0].trim();
                        String roleI = infos[1].trim();
                        intervenants.add(new Intervenant(nomI, emailI, roleI));
                    }
                    conf.setIntervenants(intervenants);
                }

                // Remplace l'événement dans la liste
                for (int i = 0; i < evenements.size(); i++) {
                    if (evenements.get(i).getId().equals(evenement.getId())) {
                        evenements.set(i, evenement);
                        break;
                    }
                }
                SerialisationJson.sauvegarderEvenements(evenements, "evenements.json");
                JOptionPane.showMessageDialog(this, "Événement modifié !");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(validerBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}