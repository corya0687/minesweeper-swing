import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class Main
{

    public static void main(String[] args)
    {
        int length = 9;
        int width = 9;
        int mineCount = 10;
        GameGrid game = new GameGrid(length, width, mineCount);
        ArrayList<Point> mines = game.getMineCoordinates();

        System.out.println("test");
        for(Point p : game.getMineCoordinates())
        {
            System.out.println(p);
        }

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                System.out.print(game.getValue(i, j));
                System.out.print(",");
//                System.out.println(game.getValue(i, j));
//                if (game.mineAtPoint(i, j))
//                {
//                    System.out.println("Space at (" + i + "," + j + ")");
//                }
//                else
//                {
//                    System.out.println("all clear");
//                }
            }
            System.out.println("");

        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new GameView("Program 1");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,300);
                frame.pack();
                frame.setVisible(true);
            }
        });

    }
}
