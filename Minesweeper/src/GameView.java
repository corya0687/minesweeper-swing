import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameView extends JFrame {

    public GameView(String title)
    {
        super(title);
        GameGrid grid = new GameGrid(9,9, 9);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getLength(); j++)
            {
                c.gridx = i;
                c.gridy = j;
//                String test = String.valueOf(grid.getValue(i, j));
//                String test = i + "," + j;


                Space button = new Space();
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setLoc(i, j);
//                button.setPreferredSize(new Dimension(30,30));
//                button.setMaximumSize(new Dimension(50,50));
                button.addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {

                    }



                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                        System.out.println(e.getButton());
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
                                    button.setText("╒");
                                }
                            }
                        }

                        if(SwingUtilities.isLeftMouseButton(e))
                        {
                            if(!grid.isGameOver() && !button.isFlagged())
                            {
                                System.out.println(button.getxLoc() + "," + button.getyLoc());
                                int xVal = button.getxLoc();
                                int yVal = button.getyLoc();
                                int value = grid.getValue(xVal, yVal);
                                if(grid.mineAtPoint(xVal, yVal))
                                {
                                    System.out.println("boom");
                                    button.setText("*");
                                    button.setBackground(Color.RED);
                                    button.setOpaque(true);
                                    button.setBorderPainted(false);
//                                grid.setGameOver();

                                    // Game is over show all mine locations
                                    for(Point p : grid.getMineCoordinates())
                                    {

                                    }
                                }
                                else
                                {
                                    button.setBackground(Color.gray);
                                    button.setOpaque(true);
                                    button.setBorderPainted(false);
                                    button.setText(Integer.toString(value));
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
//
//                button.addActionListener(new ActionListener()
//                {
//                    @Override
//                    public void actionPerformed(ActionEvent e)
//                    {
//
//
//                    }
//                });

                if(grid.getValue(i, j) < 0)
                {
                    button.setText("");
                }

                add(button, c);
            }
        }

        for(Point p : grid.getMineCoordinates())
        {
            System.out.println(p.getX() + "," + p.getY());
        }



    }

}
