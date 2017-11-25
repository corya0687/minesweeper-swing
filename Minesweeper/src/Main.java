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
