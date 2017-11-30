import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                JFrame frame = new JFrame("Test");
                frame.setLayout(new BorderLayout());
                GameView game = new GameView();
                game.newGame(9,9,9);
                JButton test = new JButton("test");
                test.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        frame.remove(game);
                        game.newGame(9, 9,10);
                        frame.add(game);
                    }
                });
                frame.add(test, BorderLayout.NORTH);
                frame.add(game);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,600);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(600,300);
                frame.setVisible(true);
//                frame.pack();
//                JFrame frame = new GameView("Program 1");
//                frame.pack();
//                frame.setVisible(true);
            }
        });

    }
}
