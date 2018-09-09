/*
    Node class. Very simple. Just stores information of pixel position and neighboring nodes.
 */
public class Node {
    private int[] position = new int[2];                     //[x,y]
    private Node[] neighbors = new Node[4];     //[Top(0), Right(1), Bottom(2), Left(3)]

    public Node(int[] pos){
        position = pos;
    }

    public int getX() { return position[0]; }
    public int getY() { return position[1]; }
    public Node[] getNeighbors() { return neighbors; }
    public void setNeighbor(int pos, Node n) { neighbors[pos] = n; }
}
