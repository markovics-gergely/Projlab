package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.characters.Character;
import java.util.ArrayList;

/**
 * Ez az osztály felel a menü megjelenítésért, ahol ki lehet választani a karaktereket.
 */
public class Menu implements ControlledScreen {
    /**
     * Az játék elindításához szolgáló gomb.
     */
    @FXML
    private Button start = new Button();
    /**
     * Az eskimo karakter kiválasztására szolgáló gomb.
     */
    @FXML
    private Button eskimo = new Button();
    /**
     * Az explorer karakter kiválasztására szolgáló gomb.
     */
    @FXML
    private Button explorer = new Button();
    /**
     * A kiválasztott karakterek megjelenítésére szolgáló felület.
     */
    @FXML
    private GridPane chosenCharacters = new GridPane();
    /**
     * A képek a megjelenítéshez.
     */
    Image chBigImage = new Image("charactersBig.png");
    Image chMaxImage = new Image("charactersMax.png");
    Image startImage = new Image("start.png");

    /**
     * A saját screenjét tárolja.
     */
    private ScreenController actualScreen;
    /**
     * Lista a karakterek eltolásáról, amik ki lettek választva.
     */
    private ArrayList<Integer> characterShift = new ArrayList<>();

    /**
     * A start button implementációja. Továbblép a következő felületre, a játékba.
     */
    @FXML
    private void startAction(){
        setNextField();
    }
    /**
     * Az eskimo kiválasztásának implementációja.
     */
    @FXML
    private void eskimoAction(){
        if(characterShift.size() == 2)
            start.setVisible(true);
        if(characterShift.size() < 6) {
            characterShift.add(characterShift.size()*18*2);
            chosenCharacters.add(changeEskimoImage(1,6), characterShift.size() - 1, 0);

            eskimo.setGraphic(changeEskimoImage(0,7));
            explorer.setGraphic(changeExplorerImage(0,7));
        }
        if(characterShift.size() == 6) {
            eskimo.setVisible(false);
            explorer.setVisible(false);
        }
    }
    /**
     * Az explorer kiválasztásának implementációja.
     */
    @FXML
    private void explorerAction(){
        if(characterShift.size() == 2)
            start.setVisible(true);
        if(characterShift.size() < 6) {
            characterShift.add(characterShift.size()*18*2 + 18);
            chosenCharacters.add(changeExplorerImage(1,6), characterShift.size() - 1, 0);

            eskimo.setGraphic(changeEskimoImage(0,7));
            explorer.setGraphic(changeExplorerImage(0,7));
        }
        if(characterShift.size() == 6) {
            eskimo.setVisible(false);
            explorer.setVisible(false);
        }
    }

    /**
     * Létrehozza az Eskimo képét.
     * @param i Paraméter a karakter színének meghatározására a képből.
     * @param size A karakter mérete.
     * @return A karakterhez tartozó ImageView
     */
    private ImageView changeEskimoImage(int i, int size){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D((characterShift.size()-i)*18*size*2,0,18*size,30*size));
        if(i == 0) iv.setImage(chMaxImage);
        if(i == 1) iv.setImage(chBigImage);

        return iv;
    }
    /**
     * Létrehozza az Explorer képét.
     * @param i Paraméter a karakter színének meghatározására a képből.
     * @param size A karakter mérete.
     * @return A karakterhez tartozó ImageView
     */
    private ImageView changeExplorerImage(int i, int size){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D((characterShift.size()-i)*18*size*2+18*size,0,18*size,30*size));
        if(i == 0) iv.setImage(chMaxImage);
        if(i == 1) iv.setImage(chBigImage);

        return iv;
    }

    /**
     * Beállítja az aktuális screent.
     * @param actScreen Aktuális screen.
     */
    @Override
    public void setActualScreen(ScreenController actScreen) {
        actualScreen = actScreen;
    }
    /**
     * Beállítja a következő képernyőt.
     */
    @Override
    public void setNextField() {
        actualScreen.removeScreen(Main.menuID);
        actualScreen.loadScreen(Main.gameboardID, Main.gameboardFXML, characterShift);
        actualScreen.setScreen(Main.gameboardID);
    }
    /**
     * Inicializáláskor elsőnek meghívódo fv a képernyőn.
     * @param ch A GameBoard létrehozásakor szükséges átadni a létrehozott karakter listát.
     */
    @Override
    public void initialize(ArrayList<Integer> ch) {
        start.setVisible(false);
        start.setGraphic(new ImageView(startImage));
        start.setBackground(Background.EMPTY);
        eskimo.setGraphic(changeEskimoImage(0,7));
        eskimo.setBackground(Background.EMPTY);
        explorer.setGraphic(changeExplorerImage(0,7));
        explorer.setBackground(Background.EMPTY);
    }
}
