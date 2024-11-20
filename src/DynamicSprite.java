import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite {
    // Variables
    protected boolean isWalking = true;
    private double speed = 13;
    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrame = 100;
    private Direction direction = Direction.South;
    private int apples = 3;
    private JFrame displayZoneFrame;
    private boolean niveau2 = false;
    private boolean gagne = false;


    // Stats du personnage
    private int health = 100;
    private final int maxHealth = 100;


    // Durée d'invincibilité
    private long invincibilityTime = 0;

    /**
     * Permet de restaurer la santé du joueur
     */
    public void useApple() {
        if (apples > 0) {
            health += 25;
            if (health > maxHealth) {
                health = maxHealth;
            }
            apples--;
        }
    }

    /**
     *
     * @return nombre de pommes disponibles
     */
    public int getApples() {
        return apples;
    }

    // Constructeur de DynamicSprite
    protected DynamicSprite(Image image, double x, double y, double width, double height, JFrame displayZoneFrame) {
        super(image, x, y, width, height);
        this.displayZoneFrame = displayZoneFrame;
    }

    protected void startWalking(){
        isWalking = true;
    }
    /**
     * Permet de connaitre la direction du DynamicSprite
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Permet d'associer la variable direction à une direction
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @return santé du joueur
     */
    public int getHealth() {
        return health;
    }

    /**
     * Permet de dessiner la barre de vie au dessus du joueur
     * @param g
     */
    public void drawHealthBar(Graphics g) {
        int barWidth = 48;
        int barHeight = 6;
        int barX = (int) this.x;
        int barY = (int) this.y - 10;


        // Barre de vie (vert)
        int vieActuelle = (int) ((double) health / maxHealth * barWidth);
        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, vieActuelle, barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    // Méthode qui permet de déplacer le sprite
    protected void move() {
        if (isWalking) {
            if (direction == Direction.North) {
                this.y -= speed;
            } else if (direction == Direction.South) {
                this.y += speed;
            } else if (direction == Direction.East) {
                this.x += speed;
            } else if (direction == Direction.West) {
                this.x -= speed;
            }
        }
    }


    // Méthode qui permet de savoir si le mouvement est possible
    private boolean isMovementPossible(ArrayList<Sprite> environment) {
        Rectangle2D hitBox = new Rectangle2D.Double(x, y, (double) width, (double) height);
        double newX = x;
        double newY = y;

        if (direction == Direction.North) {
            newY = y - speed;
        } else if (direction == Direction.South) {
            newY = y + speed;
        } else if (direction == Direction.East) {
            newX = x + speed;
        } else if (direction == Direction.West) {
            newX = x - speed;
        } else {
            return true;
        }
        hitBox.setRect(newX, newY, width, height);

        for (Sprite sprite : environment) {
            if (sprite instanceof Trap){
                continue;
            }

            if (hitBox.intersects(((SolidSprite) sprite).getHitBox())) {
                if (sprite instanceof Bridge && !niveau2) {

                    try {
                        displayZoneFrame.getContentPane().removeAll();
                        Niveau2 mapTwo = new Niveau2(displayZoneFrame);

                        displayZoneFrame.getContentPane().add(mapTwo);
                        displayZoneFrame.revalidate();
                        displayZoneFrame.repaint();

                        niveau2 = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (sprite instanceof Tresor && !gagne){
                    gagne = true;
                    isWalking = false;

                    // Ecran de fin
                    JFrame winFrame = new JFrame("You won");
                    winFrame.setSize(600, 300);
                    winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    winFrame.setLocationRelativeTo(null);  // Centrer la fenêtre

                    // Création du label Game Over
                    JLabel label = new JLabel("You won", SwingConstants.CENTER);
                    label.setFont(new Font("Arial", Font.BOLD, 50));
                    label.setForeground(Color.ORANGE);

                    // Fenêtre de fin (affichage)
                    winFrame.setLayout(new BorderLayout());
                    winFrame.add(label, BorderLayout.CENTER);
                    winFrame.setVisible(true);

                }

                return false;
            }


        }
        return true;
    }

    /**
     * Vérifie si un déplacement est possible avant de l'effectuer
     * @param environnement liste des sprites qui sont les obstacles sur la map
     */
    public void moveIfPossible(ArrayList<Sprite> environnement) {
        if (isMovementPossible(environnement)) {
            move();

        }
    }


    /**
     * Méthode qui permet de diminuer la barre de vie du DynamicSprite et de lui infliger "damage" dégats
     * @param damage
     */
    public void takeDamage(int damage) {
        // Vérifier si le personnage est encore invincible
        if (System.currentTimeMillis() > invincibilityTime) {
            health -= damage;
            if (health < 0) health = 0;
        }
    }

    /**
     * Rend le personnage invincible temporairement (2 secondes)
     */
    public void becomeInvincible() {
        invincibilityTime = System.currentTimeMillis() + 2000; // Invincible pendant 2 secondes
    }

    /**
     * Méthode qui permet de vérifier si le personnage est dans l'état invincible
     * @return True si c'est le cas, False sinon.
     */
    public boolean isInvincible() {
        return System.currentTimeMillis() < invincibilityTime;
    }

    /**
     * Méthode qui permet de dessiner : le DynamicSprite, la barre de vie au dessus du personnage, le nombre de pommes restantes
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        if (isWalking) {
            int index = (int) ((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);
            int frameRow = direction.getFrameLineNumber();
            int frameWidth = 48;
            int frameHeight = 51;

            // Calcul des coordonnées pour découper dans le sprite sheet
            int srcX1 = index * frameWidth;
            int srcY1 = frameRow * frameHeight;
            int srcX2 = srcX1 + frameWidth;
            int srcY2 = srcY1 + frameHeight;

            // Dessiner une seule image correspondant à l'orientation du personnage
            g.drawImage(image,
                    (int) x, (int) y,
                    (int) x + frameWidth, (int) y + frameHeight,
                    srcX1, srcY1,
                    srcX2, srcY2,
                    null);

            drawHealthBar(g);




        }
        else{
            int index = 0;
            int frameRow = direction.getFrameLineNumber();
            int frameWidth = 48;
            int frameHeight = 51;

            // Calcul des coordonnées pour découper dans le sprite sheet
            int srcX1 = index * frameWidth;
            int srcY1 = frameRow * frameHeight;
            int srcX2 = srcX1 + frameWidth;
            int srcY2 = srcY1 + frameHeight;

            // Dessiner une seule image correspondant à l'orientation du personnage
            g.drawImage(image,
                    (int) x, (int) y,
                    (int) x + frameWidth, (int) y + frameHeight,
                    srcX1, srcY1,
                    srcX2, srcY2,
                    null);

            drawHealthBar(g);
        }
        Image appleImage = new ImageIcon("./img/appleLowRes.png").getImage();
        int appleWidth = 80;
        int appleHeight = 80;
        int PositionH = 0;
        int PositionV = 0;
        // Dessiner l'icône de la pomme
        g.drawImage(appleImage, PositionH, PositionV, appleWidth, appleHeight, null);
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.ORANGE);
        g.drawString("X pour utiliser :" + apples, (int) 80, (int) 50);
    }
}

