import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Keefer on 12/3/2017.
 */
public class GameGUI extends JFrame{

    private int length, width, mines;
    private GameView grid;

    public GameGUI(String title)
    {
        // default difficulty is easy -- 9x9 grid with 10 mines
        this.length = 9;
        this.width = 9;
        this.mines = 10;

        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout(5, 5));
        grid = new GameView();
        grid.newGame(this.length, this.width, this.mines);

        this.add(grid, BorderLayout.CENTER);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Game menu
        JMenu gameMenu = new JMenu("Game");
//        file.getAccessibleContext().setAccessibleDescription("Game options");

        // Game menu items
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem difficulty = new JMenuItem("Difficulty");
        JMenuItem exit = new JMenuItem("Exit");

        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("New Game");
                System.out.println(length);
                System.out.println(width);
                System.out.println(mines);
                remove(grid);
                grid = new GameView();
                grid.newGame(length, width, mines);
                add(grid, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        difficulty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                JLabel label = new JLabel("Please select your difficulty:");

                JRadioButton beginner = new JRadioButton("Beginner: 9x9, 10 Mines");
                JRadioButton intermediate = new JRadioButton("Intermediate: 16x16, 40 Mines");
                JRadioButton advanced = new JRadioButton("Advanced: 16x30, 99 Mines");

                beginner.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        length = 9;
                        width = 9;
                        mines = 10;
                    }
                });

                intermediate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        length = 16;
                        width = 16;
                        mines = 40;
                    }
                });

                advanced.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        length = 30;
                        width = 16;
                        mines = 99;
                    }
                });

                panel.add(beginner);
                panel.add(intermediate);
                panel.add(advanced);

                JOptionPane.showMessageDialog(null, panel);
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        gameMenu.add(newGame);
        gameMenu.add(difficulty);
        gameMenu.add(exit);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");

        // Help menu items
        JMenuItem help = new JMenuItem("Help");

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // How to play
                JOptionPane.showMessageDialog(null,"Object of the game: \n" +
                        "Find the empty squares while avoiding mines.\n" +
                        "How To Play:\n" +
                        "Left Click to explore a square. The number on the square corresponds to the number of mines adjacent to that square.\n" +
                        "Right click to flag a square you believe is a mine.\n" +
                        "If you left click a mine you lose and the game is over.\n" +
                        "Explore all non-mine spaces to win.");
            }
        });

        helpMenu.add(help);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JRadioButton createCustomGameButton()
    {
        JPanel customPanel = new JPanel();
        JTextField customLength = new JTextField();
        JTextField customWidth = new JTextField();
        JTextField customMines = new JTextField();

        customPanel.add(new JLabel("Custom: "));
        customPanel.add(customLength);
        customPanel.add(new JLabel("x"));
        customPanel.add(customWidth);
        customPanel.add(new JLabel(","));
        customPanel.add(customMines);
        customPanel.add(new JLabel(" Mines"));
        JRadioButton custom = new JRadioButton();

        return custom;
    }

}
