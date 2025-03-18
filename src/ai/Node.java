package ai;

public class Node {
    public int fCost;
    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }

}
