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
            case "eat" : field.usePlayerItem(PlayerActions.eating); break; //Munka
            case "dig": field.usePlayerItem(PlayerActions.shoveling); break; //munka
            case "wearsuit": field.usePlayerItem(PlayerActions.wearingSuit); break; //Munka
            case "save" : field.setChosenToSave(selectPlayer()); field.usePlayerItem(PlayerActions.savingWithRope); break; //Munka
            case "assemble" : field.usePlayerItem(PlayerActions.assemblingEssentials); break; //Munka
            case "ability" : field.useAbility(); break; //Munka
            case "mine": field.mineActualCell(); break; //Munka
            case "setup" : field.setPlayerWay(Way.up); break;
            case "setdown" : field.setPlayerWay(Way.down); break;
            case "setleft" : field.setPlayerWay(Way.left); break;
            case "setright" : field.setPlayerWay(Way.right); break;
            case "u" : field.movePlayer(Way.up); break; //Munka
            case "d" : field.movePlayer(Way.down); break; //Munka
            case "l" : field.movePlayer(Way.left); break; //Munka
            case "r" : field.movePlayer(Way.right); break; //Munka
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
