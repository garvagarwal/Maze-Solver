import java.util.ArrayList;

public class Solution {
    private int count;
    private int length;
    private ArrayList<Node> path;
    private boolean completed;

    public Solution(int c, ArrayList<Node> p, boolean comp){
        count = c;
        length = p.size();
        path = p;
        completed = comp;
    }

    public int getCount() {
        return count;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public boolean isCompleted() {
        return completed;
    }
}
