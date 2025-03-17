package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject{

    GamePanel gp;

    public OBJ_Door(GamePanel gp){

        this.gp = gp;

        name = "Door";
        collision = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door_close.png")));
            uTool.scaleImage(image, this.gp.tileSize, this.gp.tileSize );
        } catch (IOException e) {
            System.err.println("Error: Door image not found!");
            e.printStackTrace();
        }
    }
}
