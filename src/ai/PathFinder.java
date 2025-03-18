//package ai;
//import java.util.ArrayList;
//
//import entity.Entity;
//import main.GamePanel;
//
//
//public class PathFinder {
//
//    GamePanel gp;
//    Node[][] node;
//    ArrayList<Node> openList = new ArrayList<>();
//    public ArrayList<Node> pathList = new ArrayList<>();
//    Node startNode, goalNode, currentNode;
//    boolean goalReached = false;
//    int step = 0;
//
//    public  PathFinder(GamePanel gp){
//        this.gp = gp;
//    }
//
//    public void instantiateNodes(){
//        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
//
//        int col = 0;
//        int row = 0;
//
//        while(col <gp.maxWorldCol && row < gp.maxWorldRow) {
//
//            node[col][row] = new Node(col, row);
//
//            col++;
//
//            if(col == gp.maxWorldCol){
//                col = 0;
//                row++;
//
//            }
//        }
//    }
//
//    public void resetNodes(){
//        int col = 0;
//        int row = 0;
//
//        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
//
//            node[col][row].open = false;
//            node[col][row].checked = false;
//            node[col][row].solid = false;
//
//            col++;
//            if(col == gp.maxWorldCol) {
//                col = 0;
//                row++;
//            }
//        }
//
//        openList.clear();
//        pathList.clear();
//        goalReached = false;
//        step = 0;
//    }
//
//    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
//        resetNodes();
//
//        startNode = node[startCol][startRow];
//        currentNode = startNode;
//        goalNode = node[goalCol][goalRow];
//
//        int col = 0;
//        int row = 0;
//
//        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
//
//                // SET SOLID NODE
//                // CHECK TILES
//                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
//                if(gp.tileM.tile[tileNum].collision == true){
//                        node[col][row].solid = true;
//            }
//
//        }
//
//        getCost(node[col][row]);
//
//        col++;
//        if (col == gp.maxWorldCol){
//            col = 0;
//            row++;
//
//        }
//
//    }
//
//    public void getCost(Node node){
//        // G Cost
//
//        int xDistance = Math.abs(node.col - startNode.col);
//        int yDistance = Math.abs(node.row - startNode.row);
//        node.gCost = xDistance + yDistance;
//
//        // H Cost
//        xDistance = Math.abs(node.col - goalNode.col);
//        yDistance = Math.abs(node.row - goalNode.row);
//
//        // F COST
//
//        node.fCost = node.gCost + node.hCost;
//    }
//
//    public boolean search(){
//
//        while(goalReached == false && step < 500){
//            int col = currentNode.col;
//            int row = currentNode.row;
//
//            // CHECK THE CURRENT NODE
//            currentNode.checked = true;
//            openList.remove(currentNode);
//
//            // OPEN THE UP NODE
//            if(row - 1 >= 0) {
//                openNode(node[col][row - 1]);
//            }
//
//            // OPEN THE LEFT NODE
//            if (col - 1 >= 0){
//                openNode(node[col - 1][row]);
//            }
//
//            // OPEN THE DOWN NODE
//            if (col - 1 >= 0){
//                openNode(node[col][row + 1]);
//            }
//
//            // OPEN THE RIGHT NODE
//            if (col - 1 >= 0){
//                openNode(node[col + 1][row]);
//            }
//
//        }
//    }
//
//    public void openNode(Node node){
//        if(node.open == false && node.checked == false && node.solid) {
//            node.open = true;
//            node.parent = currentNode;
//            openList.add(node);
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
