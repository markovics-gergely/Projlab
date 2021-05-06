package graphics;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import logic.IceField;
import logic.Way;
import logic.characters.Character;
import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.items.PlayerActions;

import java.util.ArrayList;

/**
 *  Ez az osztály felel a játék tényleges kinézetéért,
 *  itt fogja tudni az aktuális játékos irányítani a játékot.
 */
public class GameBoard implements ControlledScreen {
    /**
     * Az ásó használatához tartozó gomb.
     */
    @FXML
    private Button shovel = new Button();
    /**
     * A törékeny ásó használatához tartozó gomb.
     */
    @FXML
    private Button fragileshovel = new Button();
    /**
     * Tárgy kibányászásához tartozó gomb.
     */
    @FXML
    private Button pickaxe = new Button();
    /**
     * A kötél használatához tartozó gomb.
     */
    @FXML
    private Button rope = new Button();
    /**
     * Az élelem használatához tartozó gomb.
     */
    @FXML
    private Button food = new Button();
    /**
     * A búvárruha használatához tartozó gomb.
     */
    @FXML
    private Button divingsuit = new Button();
    /**
     * A sátor használatához tartozó gomb.
     */
    @FXML
    private Button tent = new Button();
    /**
     * Az összeszerelés megkezdéséhez tartozó gomb.
     */
    @FXML
    private Button essentials = new Button();
    /**
     * A felfele irány reprezentálásához tartozó gomb. Ezt használjuk mozgásnál,
     * vagy amikor képesség használat miatt egy szomszédos cellát kell kiválasztani.
     */
    @FXML
    private Button up = new Button();
    /**
     * A jobbra irány reprezentálásához tartozó gomb. Ezt használjuk mozgásnál,
     * vagy amikor képesség használat miatt egy szomszédos cellát kell kiválasztani.
     */
    @FXML
    private Button right = new Button();
    /**
     * A balra irány reprezentálásához tartozó gomb. Ezt használjuk mozgásnál,
     * vagy amikor képesség használat miatt egy szomszédos cellát kell kiválasztani.
     */
    @FXML
    private Button left = new Button();
    /**
     * A lefele irány reprezentálásához tartozó gomb. Ezt használjuk mozgásnál,
     * vagy amikor képesség használat miatt egy szomszédos cellát kell kiválasztani.
     */
    @FXML
    private Button down = new Button();
    /**
     * A soron lévő játékos passzolásához tartozó gomb.
     */
    @FXML
    private Button pass = new Button();
    /**
     * A karakter képességének használatához tartozó gomb.
     */
    @FXML
    private Button ability = new Button();
    /**
     * A menübe való kilépéshez tartozó gomb.
     */
    @FXML
    private Button menu = new Button();
    /**
     * A játékban szereplő karaktereket jeleníti meg a képernyőn.
     */
    @FXML
    private GridPane characters = new GridPane();
    /**
     * Az aktuális játékos karakterét jeleníti meg a képernyőn.
     */
    @FXML
    private GridPane actualPlayer = new GridPane();
    /**
     * Egy panel, amire elhelyezzük a grafikus elemeket.
     */
    @FXML
    private AnchorPane gameboard = new AnchorPane();
    /**
     * Kiírja, hogy a karakternek hány egységnyi munkája van még.
     */
    @FXML
    private Label actionLabel = new Label();
    /**
     * Kiírja, hogy a karakternek mennyi testhője van még.
     */
    @FXML
    private Label bodyHeatLabel = new Label();

    /**
     * A játékhoz szükséges képek.
     */
    private Image chSmallImage = new Image("charactersSmall.png");
    private Image chMediumImage = new Image("charactersMedium.png");
    private Image chMaxImage = new Image("charactersMax.png");
    private Image availableItemsImage = new Image("availableItems.png");
    private Image unavailableItemsImage = new Image("unavailableItems.png");
    private Image bearImage = new Image("bear.png");
    private Image cellsImage = new Image("cells.png");
    private Image numbersImage = new Image("numbers.png");
    private Image lidImage = new Image("lid.png");
    private Image controlImage = new Image("control.png");

    /**
     * A játéktér kirajzolása.
     */
    private GridPane field;
    /**
     * Cellához tartozó megjelenítők csoportja.
     */
    private GridPane cellCharacter, cellCapacity, cellLid, cellBear;

    /**
     * A saját screenjét tárolja.
     */
    private ScreenController actualScreen;
    /**
     * A logikához tartozó főosztály
     */
    private IceField iceField;
    /**
     * A karaketerek eltolását tárolja
     */
    private ArrayList<Integer> charactersShift = new ArrayList<>();
    /**
     * IsAbility jelzi, hogy előtte megnyomták-e az ability gombot, az isSaving
     * pedig jelzi, hogy éppen a kötéllel mentéshez akarunk-e játékost választani.
     */
    private boolean isAbility = false, isSaving = false;

    /**
     * Visszalépés a menübe.
     */
    @FXML
    private void menuAction(){
        setNextField();
    }
    /**
     * Ásó használata.
     */
    @FXML
    private void shovelAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.shoveling);

        refreshGame();
    }
    /**
     * Törékeny ásó használata.
     */
    @FXML
    private void fragileshovelAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.fragileshoveling);

        refreshGame();
    }
    /**
     * Étel használata.
     */
    @FXML
    private void foodAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.eating);

        refreshGame();
    }
    /**
     * Búvárruha használata.
     */
    @FXML
    private void divingsuitAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.wearingSuit);

        refreshGame();
    }
    /**
     * Csákány használata.
     */
    @FXML
    private void pickaxeAction(){
        isAbility = false; isSaving = false;
        iceField.mineActualCell();

        refreshGame();
    }
    /**
     * Kötél használata.
     */
    @FXML
    private void ropeAction(){
        isAbility = false;
        isSaving = true;
    }
    /**
     * Sátor használata.
     */
    @FXML
    private void tentAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.setUpTent);

        refreshGame();
    }
    /**
     * Összeszerelés kezdeményezése.
     */
    @FXML
    private void essentialsAction(){
        isAbility = false; isSaving = false;
        iceField.usePlayerItem(PlayerActions.assemblingEssentials);

        refreshGame();
    }

    /**
     * Felfele lépés/Képesség irányának meghatározása.
     */
    @FXML
    private void upAction(){
        if(isAbility) {
            iceField.setPlayerWay(Way.up);
            iceField.useAbility();
            isAbility = false;
        }
        else iceField.movePlayer(Way.up);

        isSaving = false;

        refreshGame();
    }
    /**
     * Balra lépés/Képesség irányának meghatározása.
     */
    @FXML
    private void leftAction(){
        if(isAbility) {
            iceField.setPlayerWay(Way.left);
            iceField.useAbility();
            isAbility = false;
        }
        else iceField.movePlayer(Way.left);

        isSaving = false;

        refreshGame();
    }
    /**
     * Lefele lépés/Képesség irányának meghatározása.
     */
    @FXML
    private void downAction(){
        if(isAbility) {
            iceField.setPlayerWay(Way.down);
            iceField.useAbility();
            isAbility = false;
        }
        else iceField.movePlayer(Way.down);

        isSaving = false;

        refreshGame();
    }

    /**
     * Jobbra lépés/Képesség irányának meghatározása.
     */
    @FXML
    private void rightAction(){
        if(isAbility) {
            iceField.setPlayerWay(Way.right);
            iceField.useAbility();
            isAbility = false;
        }
        else iceField.movePlayer(Way.right);

        isSaving = false;

        refreshGame();
    }
    /**
     * Aktuális karakter körének passzolása.
     */
    @FXML
    private void passAction(){
        isAbility = false; isSaving = false;
        iceField.nextPlayer();

        refreshGame();
    }
    /**
     * Képesség használata.
     */
    @FXML
    private void abilityAction(){
        if(charactersShift.get(iceField.getActualPlayerNumber()) == iceField.getActualPlayerNumber()*18*2+18)
            isAbility = true;
        else {
            iceField.useAbility();
            refreshGame();
        }

        isSaving = false;
    }
    /**
     * Karakter kiválasztása.
     * @param mouseEvent Esetlegesen bekövetkező kattintás paraméterei.
     */
    @FXML
    private void charactersAction(MouseEvent mouseEvent){
        if(isSaving) {
            Node node = mouseEvent.getPickResult().getIntersectedNode();
            Integer x = characters.getColumnIndex(node);
            Integer y = characters.getRowIndex(node);

            if (x != null && y != null) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    iceField.setChosenToSave(y);
                    iceField.usePlayerItem(PlayerActions.savingWithRope);
                }
            }

            refreshGame();
        }
        isSaving = false;
    }

    /**
     * Felület újrarajzolása.
     */
    private void refreshGame(){
        gameChecker();
        buildActualPlayer();
        buildBackPack();
        buildCharacters();
        buildField();
    }
    /**
     * Megnézi, hogy vége van-e a játéknak.
     */
    public void gameChecker(){
        if(iceField.gameLost){
            setNextField();
        }
        if(iceField.gameWon){
            setNextField();
        }
    }

    /**
     * Tárgy képeeinek beállítása
     * @param itemType Tárgy típusa
     * @param actualButton Gomb amire rá kell rakni a képet.
     * @param x Eltolás mértéke.
     */
    public void setItemsImage(Image itemType, Button actualButton, int x){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D(84*x,0,84,84));
        iv.setImage(itemType);
        actualButton.setGraphic(iv);
        actualButton.setBackground(Background.EMPTY);
    }

    /**
     * Irányító felület képeinek beállítása.
     * @param actualButton Gomb amire rá kell rakni a képet.
     * @param x Eltolás mértéke.
     */
    public void setControlPanelImage(Button actualButton, int x){
        ImageView iv = new ImageView();
        iv.setViewport(new Rectangle2D(144*x, 0, 144, 144));
        iv.setImage(controlImage);
        actualButton.setGraphic(iv);
        actualButton.setBackground(Background.EMPTY);
    }

    /**
     * Karakter képeinek beaállítása.
     * @param chType Karakter típusa.
     * @param size Karakter mérete.
     * @param x Eltolás mértéke.
     * @return ImageView amit visszaad.
     */
    public ImageView setCharacterImage(Image chType, int size, int x){
        ImageView chImageView = new ImageView();
        Rectangle2D chRectangle = null;

        if (charactersShift.get(x) == x*18*2+18)
            chRectangle = new Rectangle2D(x * 18 * size * 2 + 18 * size, 0, 18 * size, 30 * size);
        if (charactersShift.get(x) == x*18*2)
            chRectangle = new Rectangle2D(x * 18 * size * 2,             0, 18 * size, 30 * size);
        chImageView.setImage(chType);
        chImageView.setViewport(chRectangle);

        return chImageView;
    }

    /**
     * Cella képeinek beállítása.
     * @param x Eltolás x paramétere
     * @param y Eltolás y paramétere
     * @return ImageView amit visszaad.
     */
    public ImageView setCellImage(int x, int y){
        Rectangle2D cellRectangle = null;
        if(iceField.getCell(x, y).getSnow() == 0) {
            if (iceField.getCell(x, y).getCapacity() > 0) {
                if(iceField.getCell(x,y).hasItem(PlayerActions.shoveling)) cellRectangle = new Rectangle2D(90, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.savingWithRope)) cellRectangle = new Rectangle2D(180, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.eating)) cellRectangle = new Rectangle2D(270, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.fragileshoveling)) cellRectangle = new Rectangle2D(360, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.wearingSuit)) cellRectangle = new Rectangle2D(450, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.setUpTent)) cellRectangle = new Rectangle2D(540, 0, 90, 90);
                else if(iceField.getCell(x,y).hasItem(PlayerActions.assemblingEssentials)){
                    if(iceField.getCell(x,y).getEssential().getID() == 0)
                        cellRectangle = new Rectangle2D(630, 0, 90, 90);
                    if(iceField.getCell(x,y).getEssential().getID() == 1)
                        cellRectangle = new Rectangle2D(720, 0, 90, 90);
                    if(iceField.getCell(x,y).getEssential().getID() == 2)
                        cellRectangle = new Rectangle2D(810, 0, 90, 90);
                }
                else cellRectangle = new Rectangle2D(0, 0, 90, 90);
            }
            else
                cellRectangle = new Rectangle2D(900, 0, 90, 90);
        }
        else
            cellRectangle = new Rectangle2D(990, 0, 90, 90);

        ImageView cellImageView = new ImageView();
        cellImageView.setImage(cellsImage);
        cellImageView.setViewport(cellRectangle);

        return cellImageView;
    }

    /**
     * Kapacitás képének beállítása.
     * @param x Eltolás x paramétere
     * @param y Eltolás y paramétere
     * @return ImageView amit visszaad.
     */
    public ImageView setCapacityImage(int x, int y){
        int capacity = iceField.getCell(x, y).getCapacity();
        if(capacity == iceField.getMaxPlayer()) capacity = 6;

        ImageView capacityImageView = new ImageView();
        capacityImageView.setImage(numbersImage);
        capacityImageView.setViewport(new Rectangle2D(capacity*25, 0, 25, 25));

        cellCapacity.setMaxSize(25, 25);
        GridPane.setHalignment(cellCapacity, HPos.RIGHT);
        GridPane.setValignment(cellCapacity, VPos.TOP);
        GridPane.setMargin(cellCapacity, new Insets(5, 5, 0, 5));

        return capacityImageView;
    }

    /**
     * A sátor/igloo beállítása.
     * @param x Eltolás x paramétere
     * @param y Eltolás y paramétere
     * @return ImageView amit visszaad.
     */
    public ImageView setLidImage(int x, int y){
        Rectangle2D cellRectangle = null;
        if (iceField.getCell(x, y).itHasIgloo()) cellRectangle = new Rectangle2D(0, 0, 24, 20);
        if (iceField.getCell(x, y).getTentTurnsLeft() > 0) cellRectangle = new Rectangle2D(24, 0, 24, 20);

        ImageView lidImageView = new ImageView();
        lidImageView.setImage(lidImage);
        lidImageView.setViewport(cellRectangle);

        cellLid.setMaxSize(24, 20);
        GridPane.setHalignment(cellLid, HPos.LEFT);
        GridPane.setValignment(cellLid, VPos.TOP);
        GridPane.setMargin(cellLid, new Insets(5, 5, 0, 5));

        return lidImageView;
    }

    /**
     * Medve képének beállítása.
     * @return ImageView amit visszaad.
     */
    public ImageView setBearImage(){
        ImageView bearImageView = new ImageView();
        bearImageView.setImage(bearImage);
        cellBear.setMaxSize(62, 42);
        GridPane.setHalignment(cellBear, HPos.CENTER);

        return bearImageView;
    }

    /**
     * Irányító felület összes képének beállítása.
     */
    public void buildControlPanel(){
        setControlPanelImage(up, 0);
        setControlPanelImage(left, 1);
        setControlPanelImage(right, 2);
        setControlPanelImage(down, 3);
        setControlPanelImage(pass, 4);
        setControlPanelImage(ability, 5);
        setControlPanelImage(menu, 6);
    }

    /**
     * Táska összes tárgyának képe beállítása.
     */
    public void buildBackPack(){
        setItemsImage(availableItemsImage, pickaxe, 0);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.shoveling)) setItemsImage(availableItemsImage, shovel, 1);
        else setItemsImage(unavailableItemsImage, shovel, 1);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.savingWithRope)) setItemsImage(availableItemsImage, rope, 2);
        else setItemsImage(unavailableItemsImage, rope, 2);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.eating)) setItemsImage(availableItemsImage, food, 3);
        else setItemsImage(unavailableItemsImage, food, 3);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.fragileshoveling)) setItemsImage(availableItemsImage, fragileshovel, 4);
        else setItemsImage(unavailableItemsImage, fragileshovel, 4);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.wearingSuit)) setItemsImage(availableItemsImage, divingsuit, 5);
        else setItemsImage(unavailableItemsImage, divingsuit, 5);

        if(iceField.getActualPlayer().getBackPack().hasItem(PlayerActions.setUpTent)) setItemsImage(availableItemsImage, tent, 6);
        else setItemsImage(unavailableItemsImage, tent, 6);

        if(iceField.getActualPlayer().getBackPack().getEssentialItemNumber() == 0) setItemsImage(unavailableItemsImage, essentials, 7);
        else if(iceField.getActualPlayer().getBackPack().getEssentialItemNumber() == 1){
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(0))
                setItemsImage(unavailableItemsImage, essentials, 11);
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(1))
                setItemsImage(unavailableItemsImage, essentials, 12);
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(2))
                setItemsImage(unavailableItemsImage, essentials, 13);
        }
        else if(iceField.getActualPlayer().getBackPack().getEssentialItemNumber() == 2){
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(0) && iceField.getActualPlayer().getBackPack().hasEssentialItemID(1))
                setItemsImage(unavailableItemsImage, essentials, 8);
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(0) && iceField.getActualPlayer().getBackPack().hasEssentialItemID(1))
                setItemsImage(unavailableItemsImage, essentials, 9);
            if(iceField.getActualPlayer().getBackPack().hasEssentialItemID(2) && iceField.getActualPlayer().getBackPack().hasEssentialItemID(0))
                setItemsImage(unavailableItemsImage, essentials, 10);
        }
        else if(iceField.getActualPlayer().getBackPack().getEssentialItemNumber() == 3) setItemsImage(availableItemsImage, essentials, 7);
    }

    /**
     * Aktuális karakter képének és adatainak beállítása.
     */
    public void buildActualPlayer(){
        actualPlayer.getChildren().clear();
        actualPlayer.add(setCharacterImage(chMaxImage, 7, iceField.getActualPlayerNumber()), 0, 0);

        actionLabel.setText(iceField.getActualPlayer().getActionsLeft() + "");
        bodyHeatLabel.setText(iceField.getActualPlayer().getBodyHeat()  + "" );
    }

    /**
     * Összes karakter képének beállíátsa.
     */
    public void buildCharacters(){
        characters.getChildren().clear();
        int i = 0;
        for(int y = 0; y < 6; y++) {
            characters.add(setCharacterImage(chMediumImage, 2, i), 0, y);
            i++;
            if(i == iceField.getMaxPlayer()) break;
        };
    }

    /**
     * Pálya képeinek beállítása.
     */
    public void buildField(){
        field = new GridPane();
        gameboard.getChildren().add(field);

        int point = (900-(90*iceField.getFieldLength()))/2;
        field.setLayoutX(point); field.setLayoutY(point);

        for(int x = 0; x < iceField.getFieldLength(); x++) {
            for (int y = 0; y < iceField.getFieldLength(); y++) {
                field.add(setCellImage(x, y), x, y);
                buildCell(x, y);
            }
        }
    }

    /**
     *
     * @param x X-edik cella
     * @param y y-adik cella.
     */
    public void buildCell(int x, int y){
        if(iceField.getCell(x, y).getPlayersFromCell().size() > 0) {
            cellCharacter = new GridPane();
            cellCharacter.setAlignment(Pos.BOTTOM_LEFT);
            GridPane.setMargin(cellCharacter, new Insets(9, 9, 4, 9));
            int cellPlayer = 0;

            for(int x1 = 0; x1 < 4; x1++) {
                for(int y1 = 0; y1 < 2; y1++) {
                    if (((x1 == 0 || x1 == 3) && y1 == 0) || y1 == 1) {
                        for(int i = 0; i < iceField.getMaxPlayer(); i++) {
                            if(iceField.getPlayersFromField(i) == iceField.getCell(x, y).getPlayersFromCell(cellPlayer)) {
                                cellCharacter.add(setCharacterImage(chSmallImage, 1, i), x1, y1);
                                cellPlayer++;
                                break;
                            }
                        }
                    }
                    if(cellPlayer == iceField.getCell(x, y).getPlayersFromCell().size()) break;
                }
                if(cellPlayer == iceField.getCell(x, y).getPlayersFromCell().size()) break;
            }

            field.add(cellCharacter, x, y);
        }
        if(iceField.getCell(x, y).getTentTurnsLeft() > 0 || iceField.getCell(x, y).itHasIgloo()){
            cellLid = new GridPane();
            cellLid.add(setLidImage(x, y), 0, 0);
            field.add(cellLid, x, y);
        }
        if(iceField.getCell(x, y).isCapacityKnown()) {
            cellCapacity = new GridPane();
            cellCapacity.add(setCapacityImage(x, y), 0, 0);
            field.add(cellCapacity, x, y);
        }
        if(iceField.getCell(x, y).hasBear()) {
            cellBear = new GridPane();
            cellBear.add(setBearImage(), 0, 0);
            field.add(cellBear, x, y);
        }
    }

    /**
     * Eltolásokból karaktereket ad vissza.
     * @param ch Eltolás méreteinek listája
     * @return Karaktereket ad vissza.
     */
    public ArrayList<Character> countCharacters(ArrayList<Integer> ch){
        charactersShift = ch;
        ArrayList<Character> characters = new ArrayList<>();
        int i = 0;
        for(Integer c : ch){
            if(c == i*18*2) characters.add(new Eskimo(null));
            if(c == i*18*2+18) characters.add(new Explorer(null));
            i++;
        }

        return characters;
    }

    /**
     * Beállítja az aktuális screent.
     * @param actScreen Aktuális screen.
     */
    @Override
    public void setActualScreen(ScreenController actScreen) { actualScreen = actScreen; }

    /**
     * Beállítja a következő képernyőt.
     */
    @Override
    public void setNextField() {
        actualScreen.removeScreen(Main.gameboardID);
        actualScreen.loadScreen(Main.menuID, Main.menuFXML, null);
        actualScreen.setScreen(Main.menuID);
    }
    /**
     * Inicializáláskor elsőnek meghívódo fv a képernyőn.
     * @param ch A GameBoard létrehozásakor szükséges átadni a létrehozott karakter listát.
     */
    @Override
    public void initialize(ArrayList<Integer> ch) {
        iceField = new IceField(countCharacters(ch));

        buildControlPanel();
        buildActualPlayer();
        buildBackPack();
        buildCharacters();
        buildField();
    }
}
