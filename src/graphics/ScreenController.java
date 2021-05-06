package graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import logic.characters.Character;

/**
 * Ez az osztály irányítja a képernyőket.
 */
public class ScreenController extends StackPane {
    /**
     * A screenek listája.
     */
    private HashMap<String, Node> screens = new HashMap<>();

    /**
     * Létrehozza a paramtéreként kapott adatok szerint a következő screent.
     * @param name Screen ID-ja
     * @param fxml Screen FXML ID-ja
     * @param ch A karakter lista a tovább töltéshez.
     */
    public void loadScreen(String name, String fxml, ArrayList<Integer> ch) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen actScreen = ((ControlledScreen) myLoader.getController());

            actScreen.setActualScreen(this);
            actScreen.initialize(ch);
            screens.put(name, loadScreen);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    /**
     * A létrehozott screen-t betölti.
     * @param name Ezt az ID-t tölti be.
     */
    public void setScreen(String name) {
        if (screens.get(name) != null) {
            getChildren().add(screens.get(name));
        }
        else
            System.out.println("Nem töltött be!");
    }
    /**
     * @param name A kapott ID-t kitröli.
     */
    public void removeScreen(String name){
        if (screens.get(name) != null) {
            getChildren().removeAll(screens.get(name));
            screens.remove(name);
        }
    }
}
