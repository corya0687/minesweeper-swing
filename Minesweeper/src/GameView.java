import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameView extends JPanel {

    private GameGrid grid;

    public GameView()
    {
        super();

    }

    public void newGame(int length, int width, int mines)
    {

        grid = new GameGrid(length, width, mines);


        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getLength(); j++)
            {
                c.gridx = i;
                c.gridy = j;

                Space button = new Space();
                Font newTextFont = new Font(button.getFont().getName(), Font.BOLD, 14);
                button.setTextFont(newTextFont);
                button.setLoc(i, j);
                button.setPreferredSize(new Dimension(20,20));
                button.setBorder(BorderFactory.createLineBorder(Color.gray));

                button.addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        // Double click to explore if value of button == adjacent flagged spaces
                        // Explores all non flagged spaces
                        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                            System.out.println("double clicked");
                            int xLoc = button.getxLoc();
                            int yLoc = button.getyLoc();
                            int value = grid.getValue(xLoc, yLoc);
                            if(grid.isExplored(button))
                            {
                                if(value >= 0)
                                {
                                    int adjacentFlagCount = 0;
                                    ArrayList<Space> adjacentSpaces = grid.getAdjacentSpaces(button);
                                    for(Space s : adjacentSpaces)
                                    {
                                        if(s.isFlagged())
                                        {
                                            adjacentFlagCount++;
                                        }
                                    }
                                    if(adjacentFlagCount == value)
                                    {
                                        for(Space s : adjacentSpaces)
                                        {
                                            if(!grid.isExplored(s) && !s.isFlagged())
                                            {
                                                if(grid.mineAtPoint(s.getxLoc(), s.getyLoc()))
                                                {
                                                    grid.setGameOver();
                                                    s.setBackground(Color.red);
                                                    s.setLabelText("*");
                                                }
                                                else
                                                {
                                                    grid.explore(s);
                                                }
                                            }
                                        }
                                        for(Space s : grid.getExplored())
                                        {
                                            int xVal = s.getxLoc();
                                            int yVal = s.getyLoc();
                                            value = grid.getValue(xVal, yVal);
                                            s.setBackground(new Color(200, 200, 200));
                                            s.setColor(value);
                                            s.setLabelText(Integer.toString(value));
                                        }

                                    }

                                }
                            }
                            // did you win?
                            grid.checkWinCondition();
                        }

                    }



                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                        // if right click or mac right click
                        if(SwingUtilities.isRightMouseButton(e) || e.getButton() == 3)
                        {
                            if(!grid.isGameOver())
                            {
                                if(!grid.isExplored(button)) {
                                    if (button.isFlagged()) {
                                        button.setFlagged(!button.isFlagged());
                                        button.setLabelText("");
                                    } else {
                                        button.setFlagged(!button.isFlagged());
                                        // Place holder flag text
                                        // TODO: change later.
                                        button.setLabelText("╒");
                                    }
                                }
                            }
                        }

                        if(SwingUtilities.isLeftMouseButton(e))
                        {
                            // Prevents loss on first click
                            if(grid.getMineCoordinates().isEmpty())
                            {
                                Point p = new Point(button.getxLoc(),button.getyLoc());
                                grid.generateMines(p);
                            }

                            if(!grid.isGameOver() && !button.isFlagged())
                            {
                                int xVal = button.getxLoc();
                                int yVal = button.getyLoc();

                                // Game over :(
                                if(grid.mineAtPoint(xVal, yVal))
                                {
                                    grid.setGameOver();

                                    // Game is over show all mine locations
                                    Hashtable<Point, Space> spaces = grid.getSpaces();
                                    for(Point p : grid.getMineCoordinates())
                                    {
                                        Space mine = spaces.get(p);
                                        if(!mine.isFlagged())
                                        {
                                            System.out.println("boom");
//                                            mine.setText("*");
                                            mine.setLabelText("*");
                                            mine.setBackground(Color.pink);
//                                            mine.setOpaque(true);
//                                            mine.setBorderPainted(false);
                                        }
                                        // Update values in map
                                        spaces.put(p, mine);
                                    }

                                    // Update value in map
                                    System.out.println("boom");
                                    button.setLabelText("*");
                                    button.setBackground(Color.RED);
                                    Point p = new Point(button.getxLoc(), button.getyLoc());
                                    spaces.put(p, button);
                                }
                                else
                                {
                                    // if 0 explore adjacent spaces until non 0 value is found
                                    for(Space s : grid.explore(button))
                                    {
                                        xVal = s.getxLoc();
                                        yVal = s.getyLoc();
                                        int value = grid.getValue(xVal, yVal);
                                        s.setBackground(new Color(220, 220, 220));
                                        s.setColor(value);
                                        s.setLabelText(Integer.toString(value));
                                    }
                                }
                            }

                            // If all non minespaces have been explored win condition is met.
                            System.out.println(grid.getExplored().size() + " || " + grid.getExplorableSpacesCount());

                            grid.checkWinCondition();
                        }

                    }


                    @Override
                    public void mouseEntered(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseExited(MouseEvent e)
                    {

                    }
                });


                // Add space grid mapping at point i,j
                Point p = new Point(i,j);
                grid.addSpace(p, button);

                add(button, c);
            }
        }
    }

}
