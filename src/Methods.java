import java.util.LinkedList;
import java.util.ArrayList;

/*
    This class is designed to host the various maze solving algorithms. They will each have their own method. This is
    slightly unnecessary, but it prevents the Maze class from getting too cluttered.
 */
public class Methods {

    public static Solution breadthFirstSearch(Maze m) {
        final Node START = m.getStart(), END = m.getEnd();
        final int width = m.getWidth();
        Boolean[] visited = new Boolean[width * m.getHeight()];
        Node[] pointsToward = new Node[width * m.getHeight()];
        Boolean completed = false;
        int count = 0;

        LinkedList<Node> queue = new LinkedList<>();

        //Start
        queue.add(START);
        visited[START.getX() + START.getY() * width] = true;

        for(int i = 0; i < width * m.getHeight(); i++)
            visited[i] = false;

        //General Algorithm
        while(!queue.isEmpty()){
            count += 1;
            Node cur = queue.removeFirst();
            Node[] neigh = cur.getNeighbors();

            if(cur == END){
                completed = true;
                break;
            }

            for(int i = 0; i < 4; i++){
                Node n = neigh[i];
                if(n != null){
                    int pos = n.getX() + n.getY() * width;
                    if(visited[pos] == false){
                        queue.add(n);
                        visited[pos] = true;
                        pointsToward[pos] = cur;
                    }
                }
            }
        }

        pointsToward[START.getX() + START.getY() * width] = null;
        Node cur = END;

        //Construct path from start to end
        ArrayList<Node> path = new ArrayList<>();

        while(cur != null){
            path.add(0, cur);
            cur = pointsToward[cur.getX() + cur.getY() * width];
        }

        return(new Solution(count, path, completed));
    }

    public static Solution depthFirstSearch(Maze m){
        final Node START = m.getStart(), END = m.getEnd();
        final int width = m.getWidth();
        Boolean[] visited = new Boolean[width * m.getHeight()];
        Node[] pointsToward = new Node[width * m.getHeight()];
        Boolean completed = false;
        int count = 0;

        LinkedList<Node> queue = new LinkedList<>();

        //Start
        queue.add(START);
        visited[START.getX() + START.getY() * width] = true;

        for(int i = 0; i < width * m.getHeight(); i++)
            visited[i] = false;

        //General Algorithm
        while(!queue.isEmpty()){
            count += 1;
            Node cur = queue.removeFirst();
            Node[] neigh = cur.getNeighbors();

            if(cur == END){
                completed = true;
                break;
            }

            for(int i = 0; i < 4; i++){
                Node n = neigh[i];
                if(n != null){
                    int pos = n.getX() + n.getY() * width;
                    if(visited[pos] == false){
                        queue.addFirst(n);
                        visited[pos] = true;
                        pointsToward[pos] = cur;
                    }
                }
            }
        }

        pointsToward[START.getX() + START.getY() * width] = null;
        Node cur = END;

        //Construct path from start to end
        ArrayList<Node> path = new ArrayList<>();

        while(cur != null){
            path.add(0, cur);
            cur = pointsToward[cur.getX() + cur.getY() * width];
        }

        return(new Solution(count, path, completed));
    }

    public static Solution leftTurn(Maze m){
        final Node START = m.getStart(), END = m.getEnd();
        int count = 0;
        int heading = 2;
        final int TURN = 1;
        ArrayList<Node> path = new ArrayList<>();
        Boolean completed = false;

        //Start
        Node cur = START;

        while(true){
            Node[] neigh = cur.getNeighbors();
            path.add(cur);
            count += 1;

            if (cur == END){
                completed = true;
                break;
            }

            if(neigh[(heading - TURN + 4) % 4] != null){            //Left turn
                heading = (heading - TURN + 4) % 4;
                cur = neigh[heading];
            } else if(neigh[heading] != null){                    //Straight
                cur = neigh[heading];
            } else if(neigh[(heading + TURN) % 4] != null){     //Right turn
                heading = (heading + TURN) % 4;
                cur = neigh[heading];
            } else if(neigh[(heading + 2) % 4] != null){        //U-turn
                heading = (heading + 2) % 4;
                cur = neigh[heading];
            }

        }

        return(new Solution(count, path, completed));
    }
}
