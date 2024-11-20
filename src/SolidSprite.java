import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class SolidSprite extends Sprite{
    protected SolidSprite(Image image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    protected Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x+20, y+10, (double) width-2*20, (double) height-2*22);
    }
}
