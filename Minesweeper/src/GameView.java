import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameView extends JFrame {

    public GameView(String title)
    {
        super(title);
        GameGrid grid = new GameGrid(9,9, 10);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < grid.getWidth(); i++)
        {
            for(int j = 0; j < grid.getLength(); j++)
            {
                c.gridx = i;
                c.gridy = j;
//                String test = String.valueOf(grid.getValue(i, j));
                String test = i + "," + j;

                JButton button = new JButton(test);
//                button.setPreferredSize(new Dimension(20,20));
                button.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if(!grid.isGameOver())
                        {
                            System.out.println(button.getX()/75 + "," + button.getY()/29);
                            int xVal = button.getX()/75;
                            int yVal = button.getY()/29;
                            int value = grid.getValue(xVal, yVal);
                            if(grid.mineAtPoint(xVal, yVal))
                            {
                                button.setText("*");
                                button.setBackground(Color.RED);
                                button.setOpaque(true);
                                button.setBorderPainted(false);

                            }
                        }

                    }
                });
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
