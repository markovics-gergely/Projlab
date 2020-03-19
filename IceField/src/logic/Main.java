package logic;

import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.characters.Character;
import logic.items.PlayerActions;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> ch = new ArrayList<>();
        ch.add(new Explorer(null));
        ch.add(new Explorer(null));
        ch.add(new Eskimo(null));

        IceField field = new IceField(ch);
        while(true){
            Scanner actionScan = new Scanner(System.in);
            String ac = actionScan.nextLine();
            action(ac, field);
            if(field.gameWon){ System.out.println("Nyertetek!"); break; }
            if(field.gameLost){ System.out.println("Vesztettetek!"); break; }
        }
    }
    public static void action(String ac, IceField field){
        switch(ac){
            case "eat" : field.usePlayerItem(PlayerActions.eating); break;
            case "dig": field.usePlayerItem(PlayerActions.shovelling); break;
            case "wearsuit": field.usePlayerItem(PlayerActions.wearingSuit); break;
            case "save" : field.setChosenToSave(selectPlayer()); field.usePlayerItem(PlayerActions.savingWithRope); break;
            case "assemble" : field.usePlayerItem(PlayerActions.assemblingEssentials); break;
            case "ability" : field.useAbility(); break;
            case "mine": field.mineActualCell(); break;
            case "setup" : field.setPlayerWay(Way.up); break;
            case "setdown" : field.setPlayerWay(Way.down); break;
            case "setleft" : field.setPlayerWay(Way.left); break;
            case "setright" : field.setPlayerWay(Way.right); break;
            case "u" : field.movePlayer(Way.up); break;
            case "d" : field.movePlayer(Way.down); break;
            case "l" : field.movePlayer(Way.left); break;
            case "r" : field.movePlayer(Way.right); break;
            case "pass" : field.nextPlayer(); break;
            default : System.out.println("Nincs ilyen opció!"); break;
        }
    }
    public static int selectPlayer(){
        System.out.println("Kit akarsz kimenteni?");
        Scanner saveScan = new Scanner(System.in);
        int c = saveScan.nextInt();
        return c;
    }
}
