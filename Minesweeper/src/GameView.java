import javax.swing.*;
import java.awt.*;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameView extends JFrame {

    public GameView(String title)
    {
        super(title);
        GameGrid grid = new GameGrid(10,10, 9);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for(int i = 0; i < grid.getLength(); i++)
        {
            for(int j = 0; j < grid.getWidth(); j++)
            {
                c.gridx = i;
                c.gridy = j;
                c.insets = new Insets(1,1, 1,1);
                String test = String.valueOf(grid.getValue(i, j));
                JButton label = new JButton(test);
                label.setMinimumSize(new Dimension(10,10));
                if(grid.getValue(i, j) < 0)
                {
                    label.setText("MINE");
                }

                add(label, c);
            }
        }

    }

}
