package logic;

import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.characters.Character;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean repeat = true;
        int chNumber = 0;
        while(repeat) {
            Scanner chScan = new Scanner(System.in);
            System.out.println("Mennyien játszanak?");
            chNumber = chScan.nextInt();
            repeat = chNumber < 3 || chNumber > 6;
        }

        ArrayList<Character> ch = new ArrayList<>();
        for(int i = 0; i < chNumber; i++){
            Scanner typeScan = new Scanner(System.in);
            System.out.println(i + ". karakter típusa: (E vagy F)");
            String type = typeScan.nextLine();

            if(type.equals("E")) ch.add(new Eskimo(null));
            else ch.add(new Explorer(null));
        }

        IceField field = new IceField(ch);
    }
}
