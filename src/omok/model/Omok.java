package omok.model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class Omok extends JFrame {
    private List<Integer> xCoordinates;

    private List<Integer> yCoordinates;
    private Map<Integer, Integer> xCoordinatesRange;
    private Map<Integer, Integer> yCoordinatesRange;
    Board omok;

    private  JButton restartGameButton;
    private  JButton stopGameButton;
    private BoardPanel gameBoard;
    private JPanel mainPanel;
    private JFrame frame;

    private boolean boardVisible = false;

    private boolean isPlayer1Turn = true;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean validMove;
    private boolean isHuman;
    private JRadioButton computerButton;
    private JButton playbutton;
    private JRadioButton humanButton;
    private ButtonGroup buttonGroup;
    private boolean gameIsRunning;


    public Omok() {
        super("Omok");
        // sample UI
        //var panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        omok = new Board();//This line should be repainted
        //var gameBoard = new BoardPanel(omok);
        p1 = new Player("Mike", true);
        p2 = new Player("Andre", true);
        p1=omok.getPlayers()[0];
        p2=omok.getPlayers()[1];
        currentPlayer=omok.getPlayers()[2];
        validMove=false;
        humanButton = new JRadioButton("Human");
        computerButton = new JRadioButton("Computer");
        buttonGroup = new ButtonGroup();
        playbutton = new JButton("play");
        gameBoard = new BoardPanel(omok);
        gameIsRunning=true;

        frame = new JFrame(); // Initialize the frame
        frame.setTitle("Omok");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        GameStartScreen();

    }
    public void GameStartScreen() {
        setJMenuBar(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));
        // sample UI
        var panel = new JPanel();
        setContentPane(panel);
        pack();
        setVisible(true);
        panel.add(new JLabel("Select opponent: "));

        //JRadioButton humanButton = new JRadioButton("Human");
        //JRadioButton computerButton = new JRadioButton("Computer");

        //ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(humanButton);
        buttonGroup.add(computerButton);

        //var playbutton = new JButton("play");

        panel.add(humanButton);
        panel.add(computerButton);
        panel.add(playbutton);
        addGameBoardActionListener(gameBoard);

        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (humanButton.isSelected()) {
                    isHuman=true;
                    p1.setIsHuman(true);
                    p2.setIsHuman(true);
                    /*
                    playbutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Play button selected
                            setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                            revalidate(); // Refresh the frame
                            pack(); // Show in the middle of the screen
                            JMenuBar();
                            Game();


                        }
                    });

                     */
                    addPlayButtonActionListener();

                }
                else if (computerButton.isSelected()) {
                    isHuman=false;
                    p1.setIsHuman(true);
                    p2.setIsHuman(false);
                    addPlayButtonActionListener();
                    //
                } else {
                    //
                }
            }
        });


    }
    private void Game() {
        //gameBoard = new BoardPanel(omok);
        xCoordinates = gameBoard.getXcoordinates();
        yCoordinates = gameBoard.getYcoordinates();
        xCoordinatesRange = gameBoard.getxCoordinatesRange();
        yCoordinatesRange = gameBoard.getyCoordinatesRange();

        //setPreferredSize(new Dimension(700, 700)); // Set the preferred size
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel); // Set the content pane for the frame
        mainPanel.add(gameBoard, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        addGameBoardActionListener(gameBoard);
        setContentPane(mainPanel);
        gameIsRunning=true;


    }
    private void JMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu customMenu = new JMenu("Game Menu");

        JMenuItem restartGameButton = new JMenuItem("Restart Game");
        JMenuItem stopGameButton = new JMenuItem("Stop Game");
        JMenuItem resignGameButton = new JMenuItem("Resign");
        // Load icons
        int desiredWidth = 15;  // Adjust the size of the icon
        int desiredHeight = 15;

       Icon restartIcon = new ImageIcon(Omok.class.getResource("restartIcon.png"));
        Image originalImageR = ((ImageIcon) restartIcon).getImage();
        // Scale the image to the desired size
        Image scaledImage = originalImageR.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedRestartIcon = new ImageIcon(scaledImage);

        // Assuming desiredWidth and desiredHeight are defined earlier in your code
        Icon stopIcon = new ImageIcon(Omok.class.getResource("stopIcon.png"));
        Image originalImageStop = ((ImageIcon) stopIcon).getImage();
        // Scale the image to the desired size
        Image scaledImageStop = originalImageStop.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedStopIcon = new ImageIcon(scaledImageStop);
        // Assuming desiredWidth and desiredHeight are defined earlier in your code

        Icon resignIcon = new ImageIcon(Omok.class.getResource("resignIcon.png"));
        Image originalImageResign = ((ImageIcon) resignIcon).getImage();
        // Scale the image to the desired size
        Image scaledImageResign = originalImageResign.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        // Create a new ImageIcon from the scaled image
        Icon resizedResignIcon = new ImageIcon(scaledImageResign);

        // Set icons for the menu items
        restartGameButton.setIcon(resizedRestartIcon);
        stopGameButton.setIcon(resizedStopIcon);
        resignGameButton.setIcon(resizedResignIcon);

        customMenu.add(restartGameButton);
        customMenu.add(stopGameButton);
        customMenu.add(resignGameButton);

        menuBar.add(customMenu);
        setJMenuBar(menuBar); // Set the menu bar for the frame

        gameBoard = new BoardPanel(omok);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gameBoard, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        restartGameButton.addActionListener(e -> {
            showConfirmationDialog();

        });
        stopGameButton.addActionListener(e -> {
            omok.clear();
            GameStartScreen();
            currentPlayer=p1;
            addGameBoardActionListener(gameBoard);
            gameBoard.setGameOver(false);
            gameIsRunning=false;

        });
        resignGameButton.addActionListener(e -> {
            //omok.switchTurns();
            omok.switchTurns();
            gameBoard.setGameOver(true);
            removeGameBoardActionListener(gameBoard);
            gameBoard.repaint();
            gameIsRunning=false;



        });

        JToolBar toolBar = new JToolBar();

        // Create buttons for the toolbar
        JButton button1 = new JButton("Info");


        // Add action listeners to the buttons
        button1.addActionListener(e -> {
            // Display an information message when button1 is clicked
            String message = "Welcome to the Omok Game!\n The player who achieves five stones in a row wins.";
            String title = "Omok Game Info";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
        // Add buttons to the toolbar
        toolBar.add(button1);
        // Add the toolbar to the main panel
        mainPanel.add(toolBar, BorderLayout.SOUTH);
    }
    private void showConfirmationDialog() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to restart the game?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // User clicked Yes-->
            omok.clear();
            gameBoard.repaint();
            currentPlayer = p1;
            omok.setCurrentPlayer(p1);
            isPlayer1Turn = true;
            addGameBoardActionListener(gameBoard);
            gameBoard.setGameOver(false);
            gameIsRunning=true;
        }
    }

    public void addGameBoardActionListener(BoardPanel gameBoard){

        gameBoard.addMouseListener(new java.awt.event.MouseAdapter(){


            public void mouseClicked(java.awt.event.MouseEvent evt) {
                /*
                if (evt.getX() == 0 && evt.getY() == 0) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(Color.BLACK);
                    g.fillOval(95, 95, 10, 10); // Draw a small circle at (100, 100)
                }

                 */

                Graphics g = gameBoard.getGraphics();

                xCoordinates=gameBoard.getXcoordinates();
                yCoordinates=gameBoard.getYcoordinates();
                //System.out.println(evt.getX());
                //System.out.println(xCoordinates);
                //System.out.println(yCoordinates);
                xCoordinatesRange=gameBoard.getxCoordinatesRange();
                yCoordinatesRange=gameBoard.getyCoordinatesRange();

                if (xCoordinates.contains(evt.getX()) && yCoordinates.contains(evt.getY())) {
                    //System.out.println(xCoordinates);
                    //System.out.println(yCoordinates);

                    omok=getBoard();

                    int x =gameBoard.getXcoordinatesOnPanel().get(xCoordinatesRange.get(evt.getX()));
                    int y=gameBoard.getYcoordinatesOnPanel().get(yCoordinatesRange.get(evt.getY()));
                    System.out.println(gameBoard.getXcoordinatesOnPanel());
                    System.out.println(xCoordinatesRange.get(evt.getX()));
                    System.out.println(x);
                    System.out.println(y);




                    //================

                    currentPlayer=omok.getCurrentPlayer();


                    if(omok.isEmpty(x,y)&&!omok.isOccupied(x,y)){
                        omok.placeStone(x,y,currentPlayer);
                        if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                            removeGameBoardActionListener(gameBoard);
                            gameBoard.paintComponent(g);
                            gameIsRunning=false;

                        }
                        else{
                            omok.switchTurns();
                            gameBoard.paintComponent(g);
                        }

                        currentPlayer=omok.getCurrentPlayer();
                        if(gameIsRunning&&!currentPlayer.isHuman()){
                            currentPlayer.automateMove(omok);
                            if(omok.isWonBy(p1)||omok.isWonBy(p2)){
                                removeGameBoardActionListener(gameBoard);
                                gameBoard.paintComponent(g);
                                gameIsRunning=false;
                            }
                            else{
                                omok.switchTurns();
                                gameBoard.paintComponent(g);
                            }

                        }

                    }


                    //gameBoard.paintComponent(g);






/*
                    //print players turn
                    g.setColor(Color.BLACK);
                    p1=omok.getPlayers()[0];
                    p2=omok.getPlayers()[1];
                    currentPlayer=omok.getPlayers()[2];
                    if(currentPlayer==p1){
                        g.drawString("Player 1's Turn",50,50);
                    }
                    else{
                        g.drawString("Player 2's Turn",50,50);
                    }

 */





                    //g.fillOval(xCoordinatesRange.get(evt.getX())-15, yCoordinatesRange.get(evt.getY())-15, 30, 30); // Draw a small circle at (100, 100)
                }


            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (evt.getX() == 75 && evt.getY() == 65) {
                    Graphics g = gameBoard.getGraphics();
                    g.setColor(gameBoard.getBackground());
                    g.fillOval(95, 95, 10, 10); // Erase the circle at (100, 100)
                }
            }
        });
    }
    public void removeGameBoardActionListener(BoardPanel gameBoard){
        gameBoard.removeMouseListener(gameBoard.getMouseListeners()[0]);

    }
    public void addPlayButtonActionListener(){
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Play button selected
                setPreferredSize(new Dimension(700, 700)); // Set the preferred size to 700x700 because it wasn't update it after calling the method
                revalidate(); // Refresh the frame
                pack(); // Show in the middle of the screen
                JMenuBar();
                Game();
                gameIsRunning=true;


            }
        });
    }
    public void checkForWin(){
        if(omok.isWonBy(p1)||omok.isWonBy(p2)){
            removeGameBoardActionListener(gameBoard);



        }
    }





    public Board getBoard(){
        return omok;
    }





}