package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{

    GamePanel gp;

    public OBJ_Heart(GamePanel gp){

        this.gp = gp;

        name = "Heart";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_blank.png")));
            image = uTool.scaleImage(image, this.gp.tileSize, this.gp.tileSize );
            image2 = uTool.scaleImage(image2, this.gp.tileSize, this.gp.tileSize );
            image3 = uTool.scaleImage(image3, this.gp.tileSize, this.gp.tileSize );

        } catch (IOException e) {
            System.err.println("Error: Heart image not found!");
            e.printStackTrace();
        }
        collision = true;
    }
}
