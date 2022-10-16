/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.model;

import Project2.service.DBManager;
import Project2.controller.FarmController;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tim
 */
public class Client {

    static FarmController farm;
    static DBManager dbManager;
    static String currentUser;

    public static void main(String[] args) {
        farm = new FarmController();
        dbManager = new DBManager();
        dbManager.establishConnection();
        Random random = new Random();

        //Farm configuration window.
        JFrame frameFarm = new JFrame("Farm Configuration");
        Container farmPane = frameFarm.getContentPane();
        farmPane.setLayout(new BoxLayout(farmPane, BoxLayout.Y_AXIS));
        frameFarm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelFarmHeader = new JPanel();
        panelFarmHeader.setPreferredSize(new Dimension(600, 100));
        JLabel labelFarmHeader = new JLabel("Java Farm");
        labelFarmHeader.setFont(new Font("Serif", Font.BOLD, 48));
        JLabel labelFarmSubHeader = new JLabel();
        labelFarmSubHeader.setFont(new Font("Sans Serif", Font.ITALIC, 14));
        panelFarmHeader.add(labelFarmHeader);
        panelFarmHeader.add(labelFarmSubHeader);

        JPanel panelFarmConfig = new JPanel();
        panelFarmConfig.setPreferredSize(new Dimension(600, 100));

        JLabel labelFarmName = new JLabel("Farm name: ");
        JTextField textFieldFarmName = new JTextField();
        textFieldFarmName.setPreferredSize(new Dimension(100, 20));
        JButton buttonSubmit = new JButton("Submit");
        JPanel panelGif = new JPanel();
        panelGif.setPreferredSize(new Dimension(600, 500));
        JLabel labelGif = new JLabel(new ImageIcon("./resources/dicerolling.gif"));
        JLabel labelResultPrefix = new JLabel();
        JLabel labelResult = new JLabel();
        JLabel labelFarmNameResult = new JLabel();
        labelResult.setPreferredSize(new Dimension(100, 20));
        labelGif.setPreferredSize(new Dimension(500, 500));
        panelGif.add(labelResultPrefix);
        panelGif.add(labelResult);
        panelGif.add(labelGif);

        //rendered farm window - main game.
        JFrame frameGame = new JFrame();
        frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGame.setPreferredSize(new Dimension(1000, 800));
        frameGame.setLocationRelativeTo(null);

        JPanel panelGameHeader = new JPanel();
        panelGameHeader.setPreferredSize(new Dimension(1000, 100));
        JPanel panelGame = new JPanel();
        panelGame.setPreferredSize(new Dimension(1000, 600));
        JPanel panelConfig = new JPanel();
        panelConfig.setPreferredSize(new Dimension(1000, 100));

        frameGame.add(panelGameHeader);
        frameGame.add(panelGame);
        frameGame.add(panelConfig);

        frameGame.pack();
        frameGame.setVisible(false);

        Timer timerGame = new Timer(100, event -> {
//            if (!farm.getFarm().isPlaying()) {
//                ((Timer) event.getSource()).stop();
//            }
            frameGame.setTitle(farm.getFarm().getName() + " - Day: " + farm.getFarm().getDay() + " - Energy: " + farm.getFarm().getEnergy() + " - Gold: " + farm.getFarm().getGold());
            while (farm.getFarm().isPlaying()) {
            }
        });

        Timer timer = new Timer(25, event -> {
            int rand = random.nextInt(1000);
            labelResult.setText("" + rand);
            if (random.nextInt(1000) <= 20) {
                //each Timer-tick (25ms) has a 2% chance of stopping the dice-roll and starting the game.
                farm.getFarm().setGold(rand);
                frameGame.setTitle(farm.getFarm().getName());
                frameFarm.setVisible(false);
                frameGame.setVisible(true);
                timerGame.start();
                ((Timer) event.getSource()).stop();
            }
        });

        buttonSubmit.addActionListener(e -> {
            try {
                farm.newFarm(textFieldFarmName.getText());
                labelResultPrefix.setText("Generating starting gold: ");
                timer.start();
            } catch (Exception ex) {
                System.out.println("Invalid farm name entered: " + ex);
                labelFarmNameResult.setText("Please enter a valid farm name!");
            }
        });

        panelFarmConfig.add(labelFarmName);
        panelFarmConfig.add(textFieldFarmName);
        panelFarmConfig.add(buttonSubmit);
        panelFarmConfig.add(labelFarmNameResult);

        frameFarm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameFarm.add(panelFarmHeader);
        frameFarm.add(panelFarmConfig);
        frameFarm.add(panelGif);
        frameFarm.setLocationRelativeTo(null);
        frameFarm.pack();
        frameFarm.setVisible(false);

        //Default, login window.
        JFrame frameLogin = new JFrame("Project 2");
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frameLogin.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JLabel labelUsername = new JLabel("Username: ");
        JLabel labelPassword = new JLabel("Password: ");
        JTextField textFieldUsername = new JTextField();
        textFieldUsername.setPreferredSize(new Dimension(100, 20));
        JTextField textFieldPassword = new JTextField();
        textFieldPassword.setPreferredSize(new Dimension(100, 20));
        JButton buttonLogin = new JButton("Login!");
        JLabel labelLogin = new JLabel();

        buttonLogin.addActionListener(e -> {
            int result = dbManager.login(textFieldUsername.getText().trim(), textFieldPassword.getText().trim());
            currentUser = textFieldUsername.getText().trim();
            switch (result) {
                case 0:
                    labelLogin.setText("Login successful!");
                    labelFarmSubHeader.setText("Welcome to your new farm - " + currentUser + "!");
                    frameLogin.setVisible(false);
                    frameFarm.setVisible(true);
                    break;
                case 1:
                    labelLogin.setText("Username and password cannot be empty.");
                    break;
                case 2:
                    labelLogin.setText("Incorrect username/password. Please try again");
                    break;
                case 3: {
                    try {
                        labelLogin.setText("Account not found in database. Creating...");
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                labelFarmSubHeader.setText("Welcome to your new farm - " + currentUser + "!");
                frameLogin.setVisible(false);
                frameFarm.setVisible(true);
                break;
                case 404:
                    labelLogin.setText("Error connecting... please try again");
                    break;
            }
        }
        );
        JPanel panelHeader = new JPanel();
        panelHeader.setPreferredSize(new Dimension(600, 100));
        JLabel labelHeader = new JLabel("Java Farm");
        labelHeader.setFont(new Font("Serif", Font.BOLD, 48));
        JLabel labelSubHeader = new JLabel("Login Page");
        labelSubHeader.setFont(new Font("Sans Serif", Font.ITALIC, 14));
        panelHeader.add(labelHeader);
        panelHeader.add(labelSubHeader);

        JPanel panelLogin = new JPanel();
        panelLogin.setPreferredSize(new Dimension(600, 100));

        panelLogin.add(labelUsername);
        panelLogin.add(textFieldUsername);
        panelLogin.add(labelPassword);
        panelLogin.add(textFieldPassword);
        panelLogin.add(buttonLogin);
        panelLogin.add(labelLogin);

        pane.add(panelHeader);
        pane.add(panelLogin);

        frameLogin.pack();
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setVisible(true);
    }

}
