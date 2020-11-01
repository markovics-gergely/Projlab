package graphics;

import java.util.ArrayList;
import logic.characters.Character;

public interface ControlledScreen {
    public void setActualScreen(ScreenController actScreen);
    public void initialize(ArrayList<Integer> ch);
    public void setNextField();
}
