package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Book extends SuperObject{

    GamePanel gp;

    public OBJ_Book(GamePanel gp){

        this.gp = gp;

        name = "Book";
        collision = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/book.png")));
            uTool.scaleImage(image, this.gp.tileSize, this.gp.tileSize );
        } catch (IOException e) {
            System.err.println("Error: Book image not found!");
            e.printStackTrace();
        }
    }
}
