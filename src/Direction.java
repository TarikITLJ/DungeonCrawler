public enum Direction{
        // Variables
        North(2), South(0), East(3), West(1);
        private final int frameLineNumber;


        Direction(int i) {
            this.frameLineNumber = i;
        }

    /**
     * Retourne le numéro de la ligne du spritesheet correspondant à l'orientation du personnage.
     * @return le numéro de la ligne du SpriteSheet correponsdant à l'orientation actuelle du personnage
     */
    public int getFrameLineNumber() {
            return frameLineNumber;
        }


}
