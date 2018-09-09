import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Solve {

    public static void main(String[] args) throws IOException {
        String path = args[0];
        String method = args[1];
        Solution solution;

        System.out.println("\nLoading Image");
        BufferedImage image = null;

        try {
            File f = new File(path);
            image = ImageIO.read(f);
            System.out.println("Success\n");
        } catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(0);
        }

        System.out.println("Creating Maze");
        long t0 = System.currentTimeMillis();
        Maze maze = new Maze(image, method);
        long t1 = System.currentTimeMillis();
        double time = (t1 - t0) / 1000.0;
        System.out.println("Nodes Created: " + maze.getCount());
        System.out.println("Time Elapsed: " + time + "\n");

        System.out.println("Starting Solve: " + method);
        t0 = System.currentTimeMillis();
        maze.solve();
        t1 = System.currentTimeMillis();
        time = (t1 - t0) / 1000.0;
        solution = maze.getSolution();
        System.out.println("Solved");
        System.out.println("Time Elapsed: " + time);
        System.out.println("Nodes Explored: " + solution.getCount());

        if (solution.isCompleted())
            System.out.println("Path Found. Length: " + solution.getLength() + "\n");
        else {
            System.out.println("No Path Found");
            System.exit(0);
        }

        //Writing and saving the drawn image
        System.out.println("Saving Image");

        BufferedImage rGBImage = new BufferedImage(image.getWidth(), image.getHeight(), 1);

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                rGBImage.setRGB(x, y, image.getRGB(x, y));
        ArrayList<Node> sol = solution.getPath();

        //Draws path on the
        Node cur, next;
        for (int i = 0; i < solution.getLength() - 1; i++) {
            cur = sol.get(i);
            next = sol.get(i + 1);
            int cX = cur.getX(), cY = cur.getY(), nX = next.getX(), nY = next.getY();
            int g = (int) ((1.0 * i / solution.getLength()) * 255);
            int p = (0 << 16) | (g << 8) | 255;


            if (cX == nX)                          //X's equal - Vertical line
                for (int yPos = Math.min(cY, nY); yPos < Math.max(cY, nY + 1); yPos++)
                    rGBImage.setRGB(cur.getX(), yPos, p);
            else if (cY == nY)                    //Y's equal - Horizontal line
                for (int xPos = Math.min(cX, nX); xPos < Math.max(cX, nX + 1); xPos++)
                    rGBImage.setRGB(xPos, cur.getY(), p);
        }

        try {
            File f = new File(path.replaceFirst(".png", "_solved.png"));
            ImageIO.write(rGBImage, "png", f);
        } catch (IOException e) {
            System.out.println("Error Saving Image: " + e);
        }

        System.out.println("Done");
    }
}