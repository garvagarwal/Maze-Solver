import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class Solve extends Application implements EventHandler<ActionEvent>{

    Button btnFile, btnSolve, btnSave;
    File maze;
    BufferedImage image;
    ImageView im;
    HBox left;
    VBox center;
    Label mazePath;

    public static void main(String[] args){
        launch(args);

//        String path;
//        String method;
//        Solution solution;
//
//        System.out.println("\nLoading Image");
//        BufferedImage image = null;
//
//        try {
//            File f = new File(path);
//            image = ImageIO.read(f);
//            System.out.println("Success\n");
//        } catch (IOException e) {
//            System.out.println("Error: " + e);
//            System.exit(0);
//        }
//
//        System.out.println("Creating Maze");
//        long t0 = System.currentTimeMillis();
//        Maze maze = new Maze(image, method);
//        long t1 = System.currentTimeMillis();
//        double time = (t1 - t0) / 1000.0;
//        System.out.println("Nodes Created: " + maze.getCount());
//        System.out.println("Time Elapsed: " + time + "\n");
//
//        System.out.println("Starting Solve: " + method);
//        t0 = System.currentTimeMillis();
//        maze.solve();
//        t1 = System.currentTimeMillis();
//        time = (t1 - t0) / 1000.0;
//        solution = maze.getSolution();
//        System.out.println("Solved");
//        System.out.println("Time Elapsed: " + time);
//        System.out.println("Nodes Explored: " + solution.getCount());
//
//        if(solution.isCompleted())
//            System.out.println("Path Found. Length: " + solution.getLength() + "\n");
//        else {
//            System.out.println("No Path Found");
//            System.exit(0);
//        }
//
//
//
//        //Writing and saving the drawn image
//        System.out.println("Saving Image");
//
//        BufferedImage rGBImage = new BufferedImage(image.getWidth(), image.getHeight(), 1);
//
//        for(int y = 0; y < image.getHeight(); y++)
//            for(int x = 0; x < image.getWidth(); x++)
//                rGBImage.setRGB(x, y, image.getRGB(x, y));
//        ArrayList<Node> sol = solution.getPath();
//
//        //Draws path on the
//        Node cur, next;
//        for(int i = 0; i < solution.getLength() - 1; i++){
//            cur = sol.get(i);
//            next = sol.get(i + 1);
//            int cX = cur.getX(), cY = cur.getY(), nX = next.getX(), nY = next.getY();
//            int g = (int)((1.0 * i / solution.getLength()) * 255);
//            int p = (0 << 16) | (g << 8) | 255;
//
//
//            if(cX == nX)                          //X's equal - Vertical line
//                for(int yPos = Math.min(cY, nY); yPos < Math.max(cY, nY + 1); yPos++)
//                    rGBImage.setRGB(cur.getX(), yPos, p);
//            else if(cY == nY)                    //Y's equal - Horizontal line
//                for(int xPos = Math.min(cX, nX); xPos < Math.max(cX, nX + 1); xPos++)
//                    rGBImage.setRGB(xPos, cur.getY(), p);
//        }
//
//        try{
//            File f = new File(path.replaceFirst(".png", "_solved.png"));
//            ImageIO.write(rGBImage, "png", f);
//        } catch (IOException e) {
//            System.out.println("Error Saving Image: " + e);
//        }
//
//        System.out.println("Done");
    }


    @Override
    public void start(Stage primaryStage){
        center = new VBox(5);
        left = new HBox(25);

        image = null;

        //Stage inits
        primaryStage.setTitle("Maze Solver");

        //Methods Drop Down
        ChoiceBox<String> methods = new ChoiceBox<>();
        methods.getItems().addAll("Breadth First Search", "Depth First Search", "Left Turn");
        methods.setValue("Breadth First Search");

        //File Chooser for selecting file

        //Button for File Chooser
        btnFile = new Button("Open Maze");
        btnFile.setOnAction(this);

        //Button for Solving
        btnSolve = new Button("Solve");
        btnSolve.setOnAction(this);

        //Button for Saving
        btnSave = new Button("Save File");

        //Layouts for setting up the scenes
        left.getChildren().addAll(btnFile, methods, btnSolve, btnSave);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        left.setAlignment(Pos.BOTTOM_CENTER);
        center.setAlignment(Pos.CENTER);
        layout.setBottom(left);
        layout.setCenter(center);

        Scene scene = new Scene(layout, 750, 750);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == btnFile){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/Users/Garv/Downloads/mazesolving-master/examples"));
            fileChooser.setTitle("Open Maze");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));
            maze = fileChooser.showOpenDialog(null);

            try{
                image = ImageIO.read(maze);
            } catch (IOException e){
                System.out.printf("Error: %s", e);
            }

            try{
                im = new ImageView(maze.toURI().toURL().toString());
            } catch (MalformedURLException e){
                System.out.println("Error: " + e);
            }

            mazePath = new Label(maze.getPath());
            im.setPreserveRatio(true);
            im.setFitHeight(image.getWidth() * 1.5);
//            printImage.setSmooth(true);

            center.getChildren().clear();
            center.getChildren().addAll(mazePath, im);
        }
    }



    public void solve(BufferedImage maze, String method){

    }
}
