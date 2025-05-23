package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    public int[][][] mapTileNum;
//    boolean drawPath = false;
    ArrayList<String> filenames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {

        this.gp = gp;

        //READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/mapfinal_tile_data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //GETTING TILE NAMES AND COLLISION FROM THE FILE
        String line;

        try {
            while ((line = br.readLine()) != null) {
                filenames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        //INITIALIZE THE TILE ARRAY BASED ON the fileNames size
        tile = new Tile[filenames.size()];
        getTileImage();

        //GET THE maxWorldCol & Row
        is = getClass().getResourceAsStream("/maps/train_map.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldRow][gp.maxWorldCol];  // Fixed row/col indexing

            br.close();

        } catch (IOException e) {
            System.out.println("Exception!");
        }
        getTileImage();
        loadMap("/maps/train_map.txt", 0);
        loadMap("/maps/secret.txt", 1);
    }

    public void getTileImage() {

        for (int i = 0; i < filenames.size(); i++) {

            String fileName;
            boolean collision;

            //Get File name
            fileName = filenames.get(i);

            //Get collission
            if (collisionStatus.get(i).equals("true")){
                collision = true;
            }
            else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
    }

    public void setup (int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/" + imageName)));
            tile [index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile [index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    // Load the map from a text file
    public void loadMap(String filePath, int map) {
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
                    mapTileNum[map][row][col] = Integer.parseInt(numbers[col]);
                }

                row++;
            }

            br.close();
            System.out.println("Map loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchMap(int map) {
        gp.currentMap = map;
        // Reset player position when switching maps (optional)
        gp.player.worldX = gp.tileSize * 35; // Set to default position
        gp.player.worldY = gp.tileSize * 52;

        // Reload the specific map
        switch(map) {
            case 0: loadMap("/maps/train_map.txt", 0); break;
            case 1: loadMap("/maps/secret.txt", 1); break;
        }
        System.out.println("Switching to map: " + map); // Add this in switchMap()
        // Force a repaint or update
        gp.repaint();
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldRow][worldCol]; // Fixed indexing

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // **Only draw tiles that are visible on screen**
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
