import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();

    /**
     * Constructeur Playground qui initialise l'environnement de jeu en lisant un fichier de type String et qui crée la map correspondante.
     * Fonctionnalités de la méthode:
     * - Charge les images des éléments de l'environnement (ici tree, grass, rock et trap) à partir d'images.
     *
     * - Lit un fichier qui contient la disposition de la carte de jeu et qui associe chaque lettre à un élément de l'environnement :
     *  - 'R' pour dessiner une roche,
     *  - 'T' pour dessiner un arbre,
     *  - 'P' pour dessiner un piège,
     *  - '' pour dessiner de l'herbe.
     *  Finalement, la méthode ajoute chaque élément à la liste environnement.
     * @param pathName Le chemin du fichier qui permet de configurer la map
     */
    public Playground(String pathName) {
        try {
            // Map 1 (level2.txt)
            final Image imageTree = ImageIO.read(new File("./img/contour.jpg"));
            final Image imageGrass = ImageIO.read(new File("./img/sol.jpg"));
            final Image imageRock = ImageIO.read(new File("./img/garg.jpg"));
            final Image imageTrap = ImageIO.read(new File("./img/piegeReel.png"));
            final Image imageBridge = ImageIO.read(new File("./img/bridge.jpg"));


            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);
            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);
            final int imageBridgeHeight = imageBridge.getHeight(null);
            final int imageBridgeWidth = imageBridge.getWidth(null);

            // Map 2 (level1.txt)
            final Image imageArbre = ImageIO.read(new File("./img/tree.png"));
            final Image imageHerbe = ImageIO.read(new File("./img/grass.png"));
            final Image imageRoche = ImageIO.read(new File("./img/rock.png"));
            final Image imagePiege = ImageIO.read(new File("./img/trap.png"));
            final Image imageTresor = ImageIO.read(new File("./img/realTresor.png"));


            final int imageTree2Width = imageArbre.getWidth(null);
            final int imageTree2Height = imageArbre.getHeight(null);
            final int imageGrass2Width = imageHerbe.getWidth(null);
            final int imageGrass2Height = imageHerbe.getHeight(null);
            final int imageRock2Width = imageRoche.getWidth(null);
            final int imageRock2Height = imageRoche.getHeight(null);
            final int imageTrap2Width = imagePiege.getWidth(null);
            final int imageTrap2Height = imagePiege.getHeight(null);

            final int imageTresorHeight = imageTresor.getHeight(null);
            final int imageTresorWidth = imageTresor.getWidth(null);


            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        // Map 1
                        case 'T':
                            environment.add(new SolidSprite(imageTree, columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight, imageTreeWidth, imageTreeHeight));
                            break;
                        case ' ':
                            environment.add(new Sprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(imageRock, columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight, imageRockWidth, imageRockHeight));
                            break;
                        case 'P':
                            environment.add(new Trap(imageTrap, columnNumber * imageTrapWidth,
                                    lineNumber * imageTrapHeight, imageTrapWidth, imageTrapHeight));
                            break;
                        case 'B':
                            environment.add(new Bridge(imageBridge,columnNumber * imageBridgeWidth,
                                    lineNumber * imageBridgeHeight, imageBridgeWidth, imageBridgeHeight));
                            break;

                        // Map 2
                        case 't':
                            environment.add(new SolidSprite(imageArbre, columnNumber * imageTree2Width,
                                    lineNumber * imageTree2Height, imageTreeWidth, imageTree2Height));
                            break;
                        case '_':
                            environment.add(new Sprite(imageHerbe, columnNumber * imageGrass2Width,
                                    lineNumber * imageGrass2Height, imageGrass2Width, imageGrass2Height));
                            break;
                        case 'r':
                            environment.add(new SolidSprite(imageRoche, columnNumber * imageRock2Width,
                                    lineNumber * imageRock2Height, imageRock2Width, imageRock2Height));
                            break;
                        case 'p':
                            environment.add(new Trap(imagePiege, columnNumber * imageTrap2Width,
                                    lineNumber * imageTrap2Height, imageTrap2Width, imageTrap2Height));
                            break;
                        case 'K':
                            environment.add(new Tresor(imageTresor, columnNumber * imageTresorWidth,
                                    lineNumber * imageTresorHeight, imageTresorWidth, imageTresorHeight));
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui retourne une liste de tous les éléments solides présents
     * @return Une liste de SolidSprites présents dans l'environnement
     */
    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    /**
     * Méthode qui retourne une liste contenant tous les sprites de l'environnement sous forme de Displayable.
     * @return Une liste d'objets Displayable de l'environnement.
     */
    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }
}

