package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application de gestion d'événements.
 * Affiche le menu principal avec tous les boutons d'action.
 */
public class Mainframe extends JFrame {
    public Mainframe() {
        // Application du Look and Feel FlatLaf pour un style moderne
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ignored) {}

        setTitle("Gestion d'événements");
        setSize(700, 500);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec fond dégradé
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(245, 245, 255);
                Color color2 = new Color(180, 200, 255);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setLayout(new BorderLayout());

        // Label de bienvenue, centré et en gras
        JLabel welcomeLabel = new JLabel("<html><b>Bienvenue dans l'application de gestion d'événements</b></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(40, 60, 120));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Panneau principal des boutons (2 groupes côte à côte) avec effet carte
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.setColor(new Color(200, 200, 220));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 40, 40);
            }
        };
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 40, 0)); // 2 colonnes, espacées

        // Premier groupe de boutons (colonne de gauche)
        JPanel group1 = new JPanel();
        group1.setOpaque(false);
        group1.setLayout(new BoxLayout(group1, BoxLayout.Y_AXIS));
        group1.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Bouton pour ajouter un événement
        JButton addEventButton = new JButton("\u2795  Ajouter un événement");
        addEventButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addEventButton.setToolTipText("Ajouter un nouvel événement");
        group1.add(addEventButton);
        group1.add(Box.createVerticalStrut(20));
        // Ouvre la boîte de dialogue d'ajout d'événement
        addEventButton.addActionListener(e -> new AddEventDialog(this).setVisible(true));

        // Bouton pour ajouter un participant
        JButton addParticipantButton = new JButton("\uD83D\uDC65  Ajouter un participant");
        addParticipantButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addParticipantButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addParticipantButton.setToolTipText("Inscrire un participant à un événement");
        group1.add(addParticipantButton);
        group1.add(Box.createVerticalStrut(20));
        // Ouvre la boîte de dialogue d'ajout de participant
        addParticipantButton.addActionListener(e -> new AddParticipantDialog(this).setVisible(true));

        // Bouton pour afficher les détails d'un événement
        JButton showDetailsButton = new JButton("\u2139\uFE0F  Afficher détails événement");
        showDetailsButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        showDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        showDetailsButton.setToolTipText("Voir les détails d'un événement");
        group1.add(showDetailsButton);
        group1.add(Box.createVerticalStrut(20));
        // Ouvre la boîte de dialogue d'affichage des détails
        showDetailsButton.addActionListener(e -> new AfficherDetails(this).setVisible(true));

        // Deuxième groupe de boutons (colonne de droite)
        JPanel group2 = new JPanel();
        group2.setOpaque(false);
        group2.setLayout(new BoxLayout(group2, BoxLayout.Y_AXIS));
        group2.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Bouton pour annuler un événement
        JButton cancelEventButton = new JButton("\u274C  Annuler événement");
        cancelEventButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        cancelEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelEventButton.setToolTipText("Annuler un événement et notifier les participants");
        group2.add(cancelEventButton);
        group2.add(Box.createVerticalStrut(20));
        // Ouvre la boîte de dialogue d'annulation
        cancelEventButton.addActionListener(e -> new Annulation(this).setVisible(true));

        // Bouton pour rechercher un événement
        JButton searchButton = new JButton("\uD83D\uDD0D  Rechercher événement");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setToolTipText("Rechercher un événement par nom, lieu ou type");
        group2.add(searchButton);
        group2.add(Box.createVerticalStrut(20));
        // Ouvre la boîte de dialogue de recherche
        searchButton.addActionListener(e -> new RechercheEvenementDialog(this).setVisible(true));

        // Bouton pour quitter l'application
        JButton quitButton = new JButton("\u23F9\uFE0F  Quitter");
        quitButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setToolTipText("Fermer l'application");
        // Ferme l'application
        quitButton.addActionListener(e -> System.exit(0));
        group2.add(quitButton);

        // Ajoute les deux groupes au panneau principal des boutons
        buttonPanel.add(group1);
        buttonPanel.add(group2);

        // Place les boutons au CENTRE du panel principal (et non en bas)
        panel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(panel);

        // Barre de statut en bas de la fenêtre pour afficher des messages à l'utilisateur
        JLabel statusBar = new JLabel("Prêt.");
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        statusBar.setOpaque(true);
        statusBar.setBackground(new Color(240, 245, 255));
        panel.add(statusBar, BorderLayout.SOUTH);

        // Exemple d'utilisation de la barre de statut après ajout d'un événement
        addEventButton.addActionListener(e -> {
            new AddEventDialog(this).setVisible(true);
            statusBar.setText("Événement ajouté ou annulé.");
        });
    }
}
