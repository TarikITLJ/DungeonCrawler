    import java.util.ArrayList;
    import java.util.List;

    public class PhysicEngine implements Engine{
        // Déclarationd des attributs
        protected ArrayList<DynamicSprite> movingSpriteList;
        protected ArrayList<Sprite> environnement;

        // Constructeur de PhysicEngine
        public PhysicEngine() {
            movingSpriteList = new ArrayList<>();
            environnement = new ArrayList<>();
        }

        /**
         * Permet l'ajout d'un DynamicSprite à la liste des sprites en mouvement
         * @param sprite
         */
        public void addToMovingSpriteList(DynamicSprite sprite) {
            movingSpriteList.add(sprite);
        }

        /**
         * Setter pour définir l'environnement du jeu
         * @param environnement
         */
        public void setStaticSpriteList(ArrayList<Sprite> environnement) {
            this.environnement = environnement;
        }


        /**
         * Met à jour les positions des Sprites en mouvement sur la carte.
         * Permet de gérer les collisions avec les pièges
         */
       @Override
       public void update() {
           for (DynamicSprite dynamicSprite : movingSpriteList) {
               dynamicSprite.moveIfPossible(environnement);

               // Vérifier la collision avec les pièges pour infliger des dégats
               for (Sprite sprite : environnement) {
                   if (sprite instanceof SolidSprite && ((SolidSprite) sprite).getHitBox().intersects(dynamicSprite.getHitBox())) {
                       if (sprite instanceof SolidSprite) {
                           if (!dynamicSprite.isInvincible()) {
                               dynamicSprite.takeDamage(40); // Le personnage prend 10 dégâts
                               dynamicSprite.becomeInvincible(); // puis il devient invincible pendant 2 secondes
                           }
                       }
                   }
               }
           }
       }
    }