import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    // Création des instances
    JFrame displayZoneFrame = new JFrame();
    RenderEngine renderEngine = new RenderEngine();
    DynamicSprite hero;
    GameEngine gameEngine;
    PhysicEngine physicEngine = new PhysicEngine();
    Playground playground;

    public Main() throws IOException {

        // Création des personnages
        hero = new DynamicSprite( ImageIO.read(new File("./img/heroTileSheetLowRes.png")),200,50,48,50,displayZoneFrame);
        // Initialisation du GameEngine
        gameEngine = new GameEngine(hero,displayZoneFrame);

        // Code de la fenêtre de jeu
        displayZoneFrame.setTitle("Dungeon Crawler");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayZoneFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.getContentPane().add(renderEngine);
        renderEngine.update();

        // Timers pour RenderEngine, GameEngine et PhysicEngine
        Timer timer = new Timer(50,(time)->renderEngine.update());
        timer.start();
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        gameTimer.start();
        Timer physicTimer = new Timer(50,(time)->physicEngine.update());
        physicTimer.start();

        // Test du Sprite
//        Sprite test = new DynamicSprite(ImageIO.read(new File("./img/tree.png")),200,100,64,64);
//        renderEngine.addToRenderList(test);    // Un arbre est bien affiché après exécution de cette ligne
        

//       // Test du SolidSprite
//        SolidSprite testSprite = new SolidSprite(ImageIO.read(new File("./img/rock.png")),250,300,64,64);
//        renderEngine.addToRenderList(testSprite);
//        renderEngine.addToRenderList(hero);
//        physicEngine.addToMovingSpriteList(hero);
//        ArrayList<Sprite> staticSprites = new ArrayList<>();
//        staticSprites.add(testSprite);
//        physicEngine.setStaticSpriteList(staticSprites);


        // Playground
        playground = new Playground("./data/level2.txt");
        physicEngine.setStaticSpriteList(playground.getSolidSpriteList());
        physicEngine.addToMovingSpriteList(hero);
        renderEngine.addToRenderList(playground.getSpriteList());
        renderEngine.addToRenderList(hero);



        // Afficher la fenêtre
        displayZoneFrame.setVisible(true);

    }
    public static void main(String[] args) throws IOException {
        // Création de la fenêtre de jeu
        new Main();
    }
}