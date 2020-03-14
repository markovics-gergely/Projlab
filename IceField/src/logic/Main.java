package logic;

import logic.characters.Eskimo;
import logic.characters.Explorer;
import logic.characters.Character;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner chScan = new Scanner(System.in);
        System.out.println("Mennyien játszanak?");
        int chNumber = chScan.nextInt();

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
