import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Niveau2 extends JPanel {
    JFrame displayZoneFrame = new JFrame();
    RenderEngine renderEngine = new RenderEngine();
    DynamicSprite hero;
    GameEngine gameEngine;
    PhysicEngine physicEngine = new PhysicEngine();
    Playground playground;

    public Niveau2(JFrame displayZoneFrame) throws IOException {

        this.setLayout(new BorderLayout());
        // Création des personnages
        hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 200, 50, 48, 50, displayZoneFrame);
        // Initialisation du GameEngine
        gameEngine = new GameEngine(hero, displayZoneFrame);

        // Code de la fenêtre de jeu
        displayZoneFrame.addKeyListener(gameEngine);
        this.add(renderEngine,BorderLayout.CENTER);
        renderEngine.update();


        // Timers pour RenderEngine, GameEngine et PhysicEngine
        Timer timer = new Timer(50, (time) -> renderEngine.update());
        timer.start();
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        gameTimer.start();
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());
        physicTimer.start();

        // Playground
        playground = new Playground("./data/level1.txt");
        physicEngine.setStaticSpriteList(playground.getSolidSpriteList());
        physicEngine.addToMovingSpriteList(hero);
        renderEngine.addToRenderList(playground.getSpriteList());
        renderEngine.addToRenderList(hero);

        // Afficher la fenêtre
        displayZoneFrame.setVisible(true);

    }
}
