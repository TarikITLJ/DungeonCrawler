import java.awt.*;

public class Sprite implements Displayable{
    // Déclaration des variables
    protected Image image;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    // Constructeur de Sprite
    protected Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter pour connaitre la largeur du Sprite
     * @return width
     */
    public double getWidth() {
        return width;
    }


    /**
     * Getter pour connaitre la hauteur du Sprite
     * @return height
     */
    public  double getHeight() {
        return height;
    }

    /**
     * Dessine l'objet en utilisant les coordonnées (x,y) et dimensions de l'objet (width, height).
     * @param g l'objet utilisé pour dessiner l'image de l'objet sur l'écran d'affichage.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }

}
