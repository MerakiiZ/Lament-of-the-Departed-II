package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{

    GamePanel gp;

    public OBJ_Key(GamePanel gp){

        this.gp = gp;

        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key_1.png")));
            uTool.scaleImage(image, this.gp.tileSize, this.gp.tileSize );
        } catch (IOException e) {
            System.err.println("Error: Key image not found!");
            e.printStackTrace();
        }
        collision = true;
    }
}
