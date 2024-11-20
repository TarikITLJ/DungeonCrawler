import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class EcranStart {
    protected JFrame ecranStart;
    private Image background;


    /**
     * Crée une fenêtre de démarrage pour le jeu.
     * Méthode qui permet d'afficher une image de fond, le titre ainsi que le bouton pour lancer le jeu.
     *
     */
    public EcranStart() {
        // Chargement de l'image de fond
        try {
            background = ImageIO.read(new File("./img/Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création de la fenêtre de démarrage
        ecranStart = new JFrame("Dungeon Crawler");
        ecranStart.setSize(600, 300);
        ecranStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ecranStart.setLocationRelativeTo(null);

        // Ajout d'un panel pour le fond
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        ecranStart.setContentPane(panel);

        // GridBagLayout permet de center les composants
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label pour le titre du jeu
        JLabel label = new JLabel("Dungeon Crawler");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        Font font = new Font("Arial", Font.BOLD, 30);
        label.setFont(font);
        panel.add(label, gbc);

        // Bouton "Lancer le jeu" sous le titre
        JButton startButton = new JButton("Lancer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));

        gbc.gridy = 1;  // On place le bouton sur la ligne suivante
        panel.add(startButton, gbc);

        // Action du bouton
        startButton.addActionListener(new ActionListener() {

            /**
             * Méthode qui permet de fermer l'écran de démarrage lorsqu'on appuie sur le bouton
             * @param e clic sur le bouton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ecranStart.dispose();
                try {
                    new Main();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Affichage de la fenêtre
        ecranStart.setVisible(true);


    }


    public static void main(String[] args) {
        new EcranStart();
    }
}
