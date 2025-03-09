package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;
//    boolean drawPath = false;
    ArrayList<String> filenames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
//        this.gp = gp;

        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/train3.txt");

//        //READ TILE DATA FILE
//        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//        //GETTING TILE NAMES AND COLLISION FROM THE FILE
//        String line;

//    try {
//        while ((line = br.readLine()) != null) {
//            filenames.add(line);
//            collisionStatus.add(br.readLine());
//        }
//        br.close();
//    } catch (IOException e){
//        e.printStackTrace();
//    }
//
//        //INITIALIZE THE TILE ARRAY BASED ON the fileNames size
//        tile = new Tile[filenames.size()];
//        getTileImage();
//
//        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];  // Fixed row/col indexing
//
//        getTileImage();
//        loadMap("/maps/train.txt");
    }

    public void getTileImage() {

        System.out.println("Image loading started");

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/black_tile.png")));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/black_tile_interactable.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/blue_tile.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/chest.png")));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/crate.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/crate1.png")));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/crate3.png")));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_down.png")));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_down_endL.png")));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_down_endR.png")));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_up.png")));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_up_endL.png")));

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/floor_carpet_up_endR.png")));

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/library_floor.png")));

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_down_L.png")));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_down_L_nofloor.png")));

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_down_r.png")));
            tile[16].collision = true;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_down_r_nofloor.png")));

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_up_L.png")));

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_up_L_nofloor.png")));

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_up_r.png")));
            tile[20].collision = true;

            tile[21] = new Tile();
            tile[21].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/seats_up_r_nofloor.png")));

            tile[22] = new Tile();
            tile[22].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_doors_left.png")));

            tile[23] = new Tile();
            tile[23].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_doors_left2.png")));

            tile[24] = new Tile();
            tile[24].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_doors__right.png")));

            tile[25] = new Tile();
            tile[25].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_doors__right2.png")));

            tile[26] = new Tile();
            tile[26].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_floor.png")));

            tile[27] = new Tile();
            tile[27].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_down.png")));
            tile[27].collision = true;

            tile[28] = new Tile();
            tile[28].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_left.png")));
            tile[28].collision = true;

            tile[29] = new Tile();
            tile[29].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_leftCorner.png")));
            tile[29].collision = true;

            tile[30] = new Tile();
            tile[30].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_leftCorner_down.png")));
            tile[30].collision = true;

            tile[31] = new Tile();
            tile[31].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_right.png")));
            tile[31].collision = true;

            tile[32] = new Tile();
            tile[32].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_rightCorner.png")));
            tile[32].collision = true;

            tile[33] = new Tile();
            tile[33].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_rightCorner_down.png")));
            tile[33].collision = true;

            tile[34] = new Tile();
            tile[34].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/train_walls_up.png")));
            tile[34].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Image loading Finished");
    }
//        for (int i = 0; i < filenames.size(); i++) {
//
//            String fileName;
//            boolean collision;
//
//            //Get File name
//            fileName = filenames.get(i);
//
//            //Get colission
//            if (collisionStatus.get(i).equals("true")){
//                collision = true;
//            }
//            else {
//                collision = false;
//            }
//
//            setup(i, fileName, collision);
//        }
//    }

//    public void setup (int index, String imageName, boolean collision){
//
//        UtilityTool uTool = new UtilityTool ();
//
//
//
//    }

    // Load the map from a text file
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.err.println("ERROR: Map file not found: " + filePath);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;

                String[] numbers = line.split(" ");

                if (numbers.length != gp.maxWorldCol) {
                    System.err.println("ERROR: Row " + row + " has " + numbers.length + " columns (expected " + gp.maxWorldCol + ")");
                    continue;
                }

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[row][col] = Integer.parseInt(numbers[col]);
                }

                row++;
            }

            br.close();
            System.out.println("Map loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldRow][worldCol]; // Fixed indexing

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // **Only draw tiles that are visible on screen**
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

            System.out.println("Drawing tile: " + tileNum + " at (" + worldCol + ", " + worldRow + ")");

        }
    }
}
