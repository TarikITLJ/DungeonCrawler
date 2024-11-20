import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameEngine implements Engine, KeyListener {
    private final DynamicSprite hero;
    private boolean gameOver = false;
    private JFrame fenetreJeu;


    private boolean  isUp;
    private boolean  isDown;
    private boolean  isRight;
    private boolean  isLeft;


    public GameEngine(DynamicSprite hero, JFrame fenetreJeu) throws IOException {
        this.hero = hero;
        this.fenetreJeu = fenetreJeu;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé ici
    }

    /**
     * Méthode qui permet de changer la direction du personnage en fonction de la touche pressée
     * @param e l'évènement de la touche pressée
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                isLeft = true;
                hero.setDirection(Direction.West);
                break;
            case KeyEvent.VK_RIGHT:
                isRight = true;
                hero.setDirection(Direction.East);
                break;
            case KeyEvent.VK_UP:
                isUp = true;
                hero.setDirection(Direction.North);
                break;
            case KeyEvent.VK_DOWN:
                isDown = true;
                hero.setDirection(Direction.South);
                break;
            case KeyEvent.VK_X:
                hero.useApple();  // Utiliser une pomme pour soigner le joueur
                break;
            default:
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                isLeft = false;
                hero.setDirection(Direction.West);
                break;
            case KeyEvent.VK_RIGHT:
                isRight = false;
                hero.setDirection(Direction.East);
                break;
            case KeyEvent.VK_UP:
                isUp = false;
                hero.setDirection(Direction.North);
                break;
            case KeyEvent.VK_DOWN:
                isDown = false;
                hero.setDirection(Direction.South);
                break;
            case KeyEvent.VK_X:
                hero.useApple();  // Utiliser une pomme pour soigner le joueur
                break;
            default:
                break;

        }
    }

    /**
     * Méthode qui permet de déplacer le personnage
     */
    public void walk(){
        if (isUp){
            hero.startWalking();
            hero.setDirection(Direction.North);
        }
        else if (isDown){
            hero.startWalking();
            hero.setDirection(Direction.South);
        }
        else if (isRight){
            hero.startWalking();
            hero.setDirection(Direction.East);
        }
        else if (isLeft){
            hero.startWalking();
            hero.setDirection(Direction.West);
        }
        else{
            hero.isWalking = false;
        }
    }

    /**
     * Vérifie si le personnage est mort (health <=0) puis affiche l'écran de fin Game Over
     */
    @Override
    public void update() {
        // Vérification de la fin du jeu
        if (hero.getHealth() <= 0 && !gameOver) {
            gameOver = true;
            showGameOverScreen();
        }
        walk();
    }



    private void restartGame() {
        fenetreJeu.dispose();
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGameOverScreen() {
        fenetreJeu.dispose();

        // Ecran de fin
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(600, 300);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setLocationRelativeTo(null);  // Centrer la fenêtre

        // Création du label Game Over
        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setForeground(Color.BLACK);

        // Création du bouton Recommencer
        JButton restartButton = new JButton("Recommencer");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(e -> restartGame());

        gameOverFrame.setLayout(new BorderLayout());
        gameOverFrame.add(label, BorderLayout.CENTER);
        gameOverFrame.add(restartButton, BorderLayout.SOUTH);

        // Fenêtre de fin (affichage)
        gameOverFrame.setVisible(true);
    }


}


