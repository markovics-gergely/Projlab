package logic;

import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.characters.Character;
import logic.items.PlayerActions;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //Ki lehet lépni a vízből
    //Nem jó a move - nem a move feladata azt tudni, a víz alatti köre alapból skippelődik (kötél használja is a move-ot, 100%-ban szüksége van rá)
    public static void main(String[] args) {
        ArrayList<Character> ch = new ArrayList<>();
        ch.add(new Explorer(null));
        ch.add(new Eskimo(null));
        ch.add(new Explorer(null));

        IceField field = new IceField(ch);
        while(true){
            Scanner actionScan = new Scanner(System.in);
            String ac = actionScan.nextLine();
            action(ac, field);
        }
    }

    public static void action(String ac, IceField field){
        switch(ac){
            case "eat" : field.usePlayerItem(PlayerActions.eating); break;
            case "dig": field.usePlayerItem(PlayerActions.shovelling); break;
            case "wearsuit": field.usePlayerItem(PlayerActions.wearingSuit); break;
            case "save" : field.usePlayerItem(PlayerActions.savingWithRope); break;
            case "assemble" : field.useEssentialItems(); break;
            case "move": field.movePlayer(); break;
            case "ability" : field.useAbility(); break;
            case "mine": field.mineActualCell(); break;
            case "wup" : field.setPlayerWay(Way.up); break;
            case "wdown" : field.setPlayerWay(Way.down); break;
            case "wleft" : field.setPlayerWay(Way.left); break;
            case "wright" : field.setPlayerWay(Way.right); break;
            case "pass" : field.nextPlayer(); break;
            default : System.out.println("Nincs ilyen opció!"); break;
        }
    }
}
