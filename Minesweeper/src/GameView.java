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
public class GameView extends JFrame {

    public GameView(String title)
    {
        super(title);
        GameGrid grid = new GameGrid(16,16, 40);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getLength(); j++)
            {
                c.gridx = i;
                c.gridy = j;

                Space button = new Space();
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setLoc(i, j);
                button.setPreferredSize(new Dimension(53,40));

//                button.setMaximumSize(new Dimension(50,50));
                button.addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                            System.out.println("double clicked");
                            int xLoc = button.getxLoc();
                            int yLoc = button.getyLoc();
                            int value = grid.getValue(xLoc, yLoc);
                            if(grid.isExplored(button))
                            {
                                if(value > 0)
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
                                            System.out.println("yes");
                                            if(!grid.isExplored(s) && !s.isFlagged())
                                            {
                                                if(grid.mineAtPoint(s.getxLoc(), s.getyLoc()))
                                                {
                                                    int xVal = s.getxLoc();
                                                    int yVal = s.getyLoc();
                                                    grid.setGameOver();
                                                    System.out.println("boom");
                                                    s.setBackground(Color.red);
                                                    s.setOpaque(true);
                                                    s.setBorderPainted(false);
                                                    s.setText("*");
                                                }
                                                else
                                                {
                                                    if(!grid.isExplored(s))
                                                    {
                                                        grid.explore(s);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }

                    }



                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                        if(SwingUtilities.isRightMouseButton(e) || e.getButton() == 3)
                        {
                            if(!grid.isGameOver())
                            {
                                if(button.isFlagged())
                                {
                                    button.setFlagged(!button.isFlagged());
                                    button.setText("");
                                }
                                else
                                {
                                    button.setFlagged(!button.isFlagged());
                                    // Place holder flag text
                                    // TODO: change later.
                                    button.setText("╒");
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
                                System.out.println(button.getxLoc() + "," + button.getyLoc());
                                int xVal = button.getxLoc();
                                int yVal = button.getyLoc();
                                int value = grid.getValue(xVal, yVal);

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
                                            mine.setText("*");
                                            mine.setBackground(Color.pink);
                                            mine.setOpaque(true);
                                            mine.setBorderPainted(false);
                                        }
                                        // Update values in map
                                        spaces.put(p, mine);
                                    }

                                    // Update value in map
                                    System.out.println("boom");
                                    button.setText("*");
                                    button.setBackground(Color.RED);
                                    button.setOpaque(true);
                                    button.setBorderPainted(false);
                                    Point p = new Point(button.getxLoc(), button.getyLoc());
                                    spaces.put(p, button);
                                }
                                else
                                {
                                    button.setBackground(Color.gray);
                                    button.setOpaque(true);
                                    button.setBorderPainted(false);
                                    button.setText(Integer.toString(value));

                                    // if 0 explore adjacent spaces until non 0 value is found
                                    for(Space s : grid.explore(button))
                                    {
                                        xVal = s.getxLoc();
                                        yVal = s.getyLoc();
                                        value = grid.getValue(xVal, yVal);
                                        s.setBackground(Color.gray);
                                        s.setOpaque(true);
                                        s.setBorderPainted(false);
                                        s.setText(Integer.toString(value));
                                    }
                                }
                            }

                            // If all non minespaces have been explored win condition is met.
                            System.out.println(grid.getExplored().size() + " || " + grid.getExplorableSpacesCount());

                            if(grid.getExplored().size() == grid.getExplorableSpacesCount())
                            {
                                grid.setGameOver();

                                System.out.println("you win");
                                for(Space s : grid.getExplored())
                                {
                                    s.setBackground(Color.GREEN);
                                }
                                for(Point s : grid.getMineCoordinates())
                                {
                                    Space mine = grid.getSpaces().get(s);
                                    mine.setBackground(Color.gray);
                                }

                            }
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

                Point p = new Point(i,j);
                grid.addSpace(p, button);

                add(button, c);
            }
        }

    }

}
