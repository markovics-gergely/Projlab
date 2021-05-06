package graphics;

import java.util.ArrayList;
import logic.characters.Character;

public interface ControlledScreen {
    /**
     * Beállítja az aktuális screent.
     * @param actScreen Aktuális screen.
     */
    public void setActualScreen(ScreenController actScreen);
    /**
     * Inicializáláskor elsőnek meghívódo fv a képernyőn.
     * @param ch A GameBoard létrehozásakor szükséges átadni a létrehozott karakter listát.
     */
    public void initialize(ArrayList<Integer> ch);
    /**
     * Beállítja a következő képernyőt.
     */
    public void setNextField();
}
