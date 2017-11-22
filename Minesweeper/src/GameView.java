import javax.swing.*;
import java.awt.*;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameView extends JFrame {

    public GameView(String title)
    {
        super(title);
        setLayout(new BorderLayout());
        GameGrid grid = new GameGrid(10,10, 9);
        this.add(grid);

    }

}
