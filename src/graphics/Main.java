package graphics;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * A Menu screen beállításához szükséges ID
     */
    public static String menuID = "menu";
    /**
     * A Menu fxml beállításához szükséges ID
     */
    public static String menuFXML = "menu.fxml";
    /**
     * A játék screen beállításához szükséges ID
     */
    public static String gameboardID = "gameboard";
    /**
     * A játék területéhez tartozó fxml beállításához szükséges ID
     */
    public static String gameboardFXML = "gameboard.fxml";

    /**
     * Beállítja kezdéskor a fő képernyőre a Menu-t
     * @param primaryStage A képernyő stage-e
     */
    @Override
    public void start(Stage primaryStage) {
        ScreenController actualScreen = new ScreenController();

        actualScreen.loadScreen(Main.menuID, Main.menuFXML, null);
        actualScreen.setScreen(Main.menuID);

        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Ice Field");
        primaryStage.setResizable(false);

        Group root = new Group();
        root.getChildren().addAll(actualScreen);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
