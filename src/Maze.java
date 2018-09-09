import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
    This class will construct the maze by nodes. This is done through a one-pass algorithm that determines if each
    pixel is a node or not. It will also connect each of the nodes to each other.
 */
public class Maze {

    private Node start, end;            //Start and end nodes
    private BufferedImage img = null;   //Space in memory to process image
    private int width, height;          //Dimensions of image
    private String pathOut;             //To write out the new image
    private int count;                  //Number of nodes
    private Node[] topNodes;            //Manages connections above
    private String method;
    private Solution sol;


    //Constuctor with pathname of image
    public Maze(BufferedImage image, String m) throws IOException{
        img = image;
        width = img.getWidth();
        height = img.getHeight();
        topNodes = new Node[width];
        method = m;

        for(int c = 0; c < width; c++)
            topNodes[c] = null;

        //Create nodes - Majority of the heavywork in maze class

        //Finding Start
        for(int x = 0; x < width; x++){
            if((img.getRGB(x,0) & 0x000000ff) > 0){
                start = new Node(new int[]{x, 0});
                topNodes[x] = start;
                count += 1;
                break;
            }
        }

        //General Body - Analyzed by looking at three nodes in a row, iterating each row
        for(int y = 1; y < height - 1; y++){
            Boolean prev = false, curr = false, next = (img.getRGB(1,y) & 0x000000ff) > 0;
            Node leftNode = null;

            for(int x = 1; x < width - 1; x++){
                prev = curr;
                curr = next;
                next = (img.getRGB(x + 1, y) & 0x000000ff) > 0;

                Node n = null;     //Place holder node object

                //Decide whether or not to make a node case by case

                //If on wall, do nothing
                if(!curr)
                    continue;

                if(prev){
                    if(next){
                        //Path path path - Look above and below to decide if its a node
                        if((img.getRGB(x, y - 1) & 0x000000ff) > 0 || (img.getRGB(x, y + 1) & 0x000000ff) > 0){
                            n = new Node(new int[]{x, y});
                            leftNode.setNeighbor(1, n);
                            n.setNeighbor(3, leftNode);
                            leftNode = n;
                        }
                    }else{
                        //Path path wall - Has to be a node
                        n = new Node(new int[]{x, y});
                        leftNode.setNeighbor(1, n);
                        n.setNeighbor(3, leftNode);
                        leftNode = null;
                    }
                }else{
                    if(next){
                        //Wall path path - Has to be a node
                        n = new Node(new int[]{x, y});
                        leftNode = n;
                    }else{
                        //Wall path wall - Look above and below to decide if its a node
                        if((img.getRGB(x, y - 1) & 0x000000ff) == 0 || (img.getRGB(x, y + 1) & 0x000000ff) == 0){
                            n = new Node(new int[]{x, y});
                        }
                    }
                }

                //First check if we have created node, then look to see if there are connections to make to above nodes
                if(n != null){
                    count += 1;

                    if((img.getRGB(x, y - 1) & 0x000000ff) > 0){     //If there is space above
                        Node t = topNodes[x];
                        t.setNeighbor(2, n);
                        n.setNeighbor(0, t);
                    }

                    if((img.getRGB(x, y + 1) & 0x000000ff) > 0)
                        topNodes[x] = n;
                    else
                        topNodes[x] = null;
                }
            }
        }

        //Finding end
        for(int x = 1; x < width - 1; x++){
            if((img.getRGB(x, height - 1) & 0x000000ff) > 0){
                end = new Node(new int[]{x, height - 1});
                Node t = topNodes[x];
                t.setNeighbor(2, end);
                end.setNeighbor(0, t);
                count += 1;
                break;
            }
        }
    }

    public Node getStart(){
        return start;
    }
    public Node getEnd(){
        return end;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getCount() { return count; }
    public Solution getSolution() { return sol; }

    public void solve(){
        if(method.equals("breadthFirstSearch"))
            sol = Methods.breadthFirstSearch(this);
        else if(method.equals("depthFirstSearch"))
            sol = Methods.depthFirstSearch(this);
        else if(method.equals("leftTurn"))
            sol = Methods.leftTurn(this);
    }
}
