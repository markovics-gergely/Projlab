package graphics;

import logic.IceField;
import logic.Way;
import logic.items.PlayerActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame {
    JButton mineButton = new JButton("Mine");
    JButton shovelButton = new JButton("Dig");
    JButton ropeButton = new JButton("Save");
    JButton foodButton = new JButton("Eat");
    JButton divingsuitButton = new JButton("Wear suit");
    JButton essentialButton = new JButton("Assemble");
    JButton moveupButton = new JButton("Move up");
    JButton moverightButton = new JButton("Move right");
    JButton movedownButton = new JButton("Move down");
    JButton moveleftButton = new JButton("Move left");
    JButton passButton = new JButton("Pass");
    JButton abilityButton = new JButton("Ability");

    JLabel sPLabel = new JLabel("Player save:");
    JLabel wayLabel = new JLabel("Way:");
    JLabel endLabel = new JLabel();
    JTextField savedPlayer = new JTextField(5);
    JTextField way = new JTextField(5);

    String action;
    IceField field;

    public GameMenu(IceField f){
        field = f;

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(500,200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(mineButton);
        add(shovelButton);
        add(ropeButton);
        add(foodButton);
        add(divingsuitButton);
        add(essentialButton);
        add(moveupButton);
        add(moveleftButton);
        add(moverightButton);
        add(movedownButton);
        add(passButton);
        add(abilityButton);

        add(sPLabel);
        add(savedPlayer);
        add(wayLabel);
        add(way);

        add(endLabel);

        buttonInput();
    }

    public void buttonInput(){
        mineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "mine";
                action(action, field);
            }
        });
        shovelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "dig";
                action(action, field);
            }
        });
        ropeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "save";
                action(action, field);
            }
        });
        foodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "eat";
                action(action, field);
            }
        });
        divingsuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "wearsuit";
                action(action, field);
            }
        });
        essentialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "assemble";
                action(action, field);
                if(endGameChecker()){ endGameScreen(); }
            }
        });
        moveupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "u";
                action(action, field);
            }
        });
        moverightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "r";
                action(action, field);
            }
        });
        movedownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "d";
                action(action, field);
            }
        });
        moveleftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "l";
                action(action, field);
            }
        });
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "pass";
                action(action, field);
            }
        });
        abilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wayInt = Integer.parseInt(way.getText());
                if(wayInt == 0) action("setup", field);
                if(wayInt == 1) action("setright", field);
                if(wayInt == 2) action("setdown", field);
                if(wayInt == 3) action("setleft", field);
                action = "ability";
                action(action, field);
            }
        });
    }

    public boolean endGameChecker(){
        if(field.gameWon) return true;
        if(field.gameLost) return true;

        return false;
    }
    public void endGameScreen(){
        if(field.gameWon) endLabel.setText("Nyertetek");
        if(field.gameLost) endLabel.setText("Vesztettetek");
    }

    public void action(String ac, IceField field){
        switch(ac){
            case "eat" : field.usePlayerItem(PlayerActions.eating); break; //Munka
            case "dig": field.usePlayerItem(PlayerActions.shoveling); break; //Munka
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
    public int selectPlayer(){
        return Integer.parseInt(savedPlayer.getText());
    }
}
