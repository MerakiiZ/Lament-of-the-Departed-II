package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Secret extends SuperObject {
    GamePanel gp;

    public OBJ_Secret(GamePanel gp){

        this.gp = gp;

        name = "Secret";
        collision = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            uTool.scaleImage(image, this.gp.tileSize, this.gp.tileSize );
        } catch (IOException e) {
            System.err.println("Error: door image not found!");
            e.printStackTrace();
        }
    }
}
