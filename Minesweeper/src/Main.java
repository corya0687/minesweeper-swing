import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class Main {

    public static void main(String[] args)
    {
        GameGrid game = new GameGrid(3,3, 8);
        ArrayList<Space> mines = game.getMineCoordinates();
        System.out.println("test");
        for(Point p : mines)
        {
            int x = (int) p.getX();
            int y = (int) p.getY();
            if(game.mineAtPoint(x,y))
            {
                System.out.println("Space at (" + x + "," + y + ")");
            }
            else
            {
                System.out.println("all clear");
            }
            System.out.println(p);
        }
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                JFrame frame = new GameView("Minesweeper Swing");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(300,300);
//                frame.pack();
//                frame.setVisible(true);
//            }
//        });
    }
}
