/**
 * Main class for the domino game
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */

package sample;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox layout = new VBox(20);
        DominoGUI mainScene = new DominoGUI(layout, 1200,1200);
        primaryStage.setTitle("Domino Game");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
