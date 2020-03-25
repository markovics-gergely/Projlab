package graphics;

import logic.IceField;
import logic.Way;
import logic.characters.Character;
import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.items.PlayerActions;

import javax.swing.*;
import java.util.ArrayList;

//Hibák:
//Rope rossz kiválasztás(Ropeban kommentelve, elv megcsinálva)
//Vége nem működik(Assemble megcsinálva, a vízbe esés és hő csökkenés nincs)
//Csökken az food amikor max a hp(Elv useItem ben javítva)
//Nem resetelődött passnál a kör (Elv megcsinálva)

//Teszt:
//Character -> adattag      -> private static int maxActions = 100;
//IceField  -> nextPlayer() -> //if (i == 0) snowStorm();
//IceCell   -> Konstruktor  -> snow = /*r.nextInt(maxSnow + 1)*/0;
public class SwingMain {
    public static void main(String[] args) {
        ArrayList<Character> ch = new ArrayList<>();
        ch.add(new Explorer(null));
        ch.add(new Explorer(null));
        ch.add(new Eskimo(null));

        IceField field = new IceField(ch);

        GameMenu game = new GameMenu(field);
    }
}
