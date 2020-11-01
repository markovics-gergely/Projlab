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

public class Menu implements ControlledScreen {
    @FXML
    private Button start = new Button();
    @FXML
    private Button eskimo = new Button();
    @FXML
    private Button explorer = new Button();
    @FXML
    private GridPane chosenCharacters = new GridPane();
    Image chBigImage = new Image("charactersBig.png");
    Image chMaxImage = new Image("charactersMax.png");
    Image startImage = new Image("start.png");

    private ScreenController actualScreen;
    private ArrayList<Integer> characterShift = new ArrayList<>();

    @FXML
    private void startAction(){
        setNextField();
    }
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

    private ImageView changeEskimoImage(int i, int size){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D((characterShift.size()-i)*18*size*2,0,18*size,30*size));
        if(i == 0) iv.setImage(chMaxImage);
        if(i == 1) iv.setImage(chBigImage);

        return iv;
    }
    private ImageView changeExplorerImage(int i, int size){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D((characterShift.size()-i)*18*size*2+18*size,0,18*size,30*size));
        if(i == 0) iv.setImage(chMaxImage);
        if(i == 1) iv.setImage(chBigImage);

        return iv;
    }

    @Override
    public void setActualScreen(ScreenController actScreen) {
        actualScreen = actScreen;
    }
    @Override
    public void setNextField() {
        actualScreen.removeScreen(Main.menuID);
        actualScreen.loadScreen(Main.gameboardID, Main.gameboardFXML, characterShift);
        actualScreen.setScreen(Main.gameboardID);
    }
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
