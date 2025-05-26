package Gui;

import javax.swing.JFrame;

public class MainGUI {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Mainframe frame = new Mainframe();
            frame.setVisible(true);
        });
    }
}
