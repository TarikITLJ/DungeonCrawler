import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderEngine extends JPanel implements Engine {
    // Variables
    private List<Displayable> renderList = new ArrayList<>();
    private GameEngine gameEngine;

    /**
     * Constructeur de RenderEngine qui clear en même temps la liste renderList
     */
    public RenderEngine() {
        this.gameEngine = gameEngine;
        renderList.clear();
    }


    /**
     * Setter pour définir la liste à afficher dans le jeu
     * @param renderList
     */
    public void setRenderList(List<Displayable> renderList) {
        this.renderList = renderList;
    }

    /**
     * Ajoute un élément à la liste renderList
     *
     * @param displayable
     */
    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    /**
     * Méthode qui permet d'ajouter tous les éléments d'une liste à la liste renderList
     * @param displayable
     */
    public void addToRenderList(List<Displayable> displayable) {
        renderList.addAll(displayable);
    }

    /**
     * Permet de dessiner tous les éléments présents dans la liste renderList et les afficher à l'écran
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Dessiner les éléments de la liste renderList
        for (Displayable d : renderList) {
            d.draw(g);
        }
    }

    /**
     * Actualise la fenêtre d'affichage en la redessinant
     */
    public void update() {
        repaint();
    }
}
