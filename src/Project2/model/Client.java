/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.model;

import Project2.service.DBManager;
import Project2.controller.FarmController;
import Project2.entity.AbstractActivity;
import Project2.entity.Animal;
import Project2.entity.AnimalFarm;
import Project2.service.AnimalActivityFactory;
import Project2.service.ImageFactory;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    static ImageFactory imageFactory;
    static AnimalActivityFactory animalActivityFactory;

    public static void main(String[] args) {
        farm = new FarmController();
        dbManager = new DBManager();
        dbManager.establishConnection();
        imageFactory = new ImageFactory();
        animalActivityFactory = new AnimalActivityFactory((AnimalFarm) farm.getFarm());

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
        labelGif.setVisible(false);
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
        frameGame.setPreferredSize(new Dimension(1414, 650));
        frameGame.setMinimumSize(new Dimension(1414, 650));
        frameGame.setLocationRelativeTo(null);
        frameGame.setLayout(new BorderLayout());
        frameGame.setContentPane(new JLabel(new ImageIcon("./resources/soil.png")));
        frameGame.setLayout(new FlowLayout());
//        Container gamePane = frameGame.getContentPane();
//        gamePane.setLayout(new BoxLayout(gamePane, BoxLayout.Y_AXIS));

        StrawPanel panelGameHeader = new StrawPanel();
        panelGameHeader.setPreferredSize(new Dimension(1354, 100));

        JLabel labelGame = new JLabel("Java Farm");
        labelGame.setFont(new Font("Serif", Font.BOLD, 48));
        panelGameHeader.add(labelGame);
        DPanel panelGame = new DPanel();
        panelGame.setPreferredSize(new Dimension(1000, 400));
        panelGame.setMaximumSize(new Dimension(1000, 400));

        StrawPanel panelConfig = new StrawPanel();
        JTextArea textGame = new JTextArea();
        textGame.setEditable(false);
        textGame.setPreferredSize(new Dimension(1150, 92));
        textGame.setText("Welcome to the farm!");
        panelConfig.add(textGame, BorderLayout.WEST);
        panelConfig.setPreferredSize(new Dimension(1354, 100));
        Choice choiceActivity = new Choice();
        choiceActivity.setName("Activities");
        choiceActivity.addItem("Feed Animals");
        choiceActivity.addItem("Water Animals");
        choiceActivity.addItem("Sleep/save");
        panelConfig.add(choiceActivity, BorderLayout.EAST);

        choiceActivity.addItemListener(l -> {
            AbstractActivity activity = animalActivityFactory.get("" + l.getItem().toString());
            activity.start();
        });

        JPanel panelAnimals = new JPanel();
        JList listAnimals = new JList();

        JScrollPane scrollAnimals = new JScrollPane(listAnimals);
        scrollAnimals.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollAnimals.setPreferredSize(new Dimension(320, 390));
        panelAnimals.add(scrollAnimals, BorderLayout.EAST);

        frameGame.add(panelGameHeader);
        frameGame.add(panelGame, BorderLayout.WEST);
        frameGame.add(panelAnimals, BorderLayout.EAST);
        frameGame.add(panelConfig);

        frameGame.pack();
        frameGame.setVisible(false);

        Timer timerGame = new Timer(25, event -> {
            frameGame.setTitle(farm.getFarm().getName() + " - Day: " + farm.getFarm().getDay() + " - Energy: " + farm.getFarm().getEnergy() + " - Gold: " + farm.getFarm().getGold());
            if (farm.getFarm().isPlaying()) {
                panelGame.revalidate();
                panelGame.repaint();
                listAnimals.setListData(farm.getAnimals().toArray());
            }
        });

        Timer timer = new Timer(25, event -> {
            labelGif.setVisible(true);
            int rand = random.nextInt(1000);
            labelResult.setText("" + rand);
            if (random.nextInt(1000) <= 20) {
                //each Timer-tick (25ms) has a 2% chance of stopping the dice-roll and starting the game.
                farm.getFarm().setGold(rand);
                ((AnimalFarm) farm.getFarm()).addAnimal("PIG");
                ((AnimalFarm) farm.getFarm()).addAnimal("HORSE");
                frameGame.setTitle(farm.getFarm().getName());
                frameFarm.setVisible(false);
                frameGame.setVisible(true);
                labelGif.setVisible(false);
                ((Timer) event.getSource()).stop();
                panelGameHeader.repaint();
                timerGame.start();
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

    protected static class StrawPanel extends JPanel {

        public StrawPanel() {
            super();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(imageFactory.getImage("header"), 0, 0, this);
        }
    }

    protected static class DPanel extends JPanel {

        public DPanel() {
            super();
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(imageFactory.getImage("background"), 0, 0, this);
            for (Animal a : ((AnimalFarm) farm.getFarm()).getAnimals()) {
                g.drawImage(imageFactory.getImage(a.getType()), a.getX(), a.getY(), this);
            }
        }

    }
}
