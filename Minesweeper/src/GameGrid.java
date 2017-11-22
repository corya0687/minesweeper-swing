import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameGrid extends JPanel {

    private Integer[][] board;
    private ArrayList<Point> mineCoordinates;
    private int length, width;

    public GameGrid(int length, int width, int mineCount)
    {
        this.length = length;
        this.width = width;
        board = new Integer[][];
        mineCoordinates = new ArrayList<>();

        for(int i = 0; i < length; i++)
        {
            for(int j = 0; j < width; j++)
            {
                board[i][j] = 0;
            }
        }

        // initialize mines into grid
        for(int i = 0; i < mineCount; i++)
        {
            int potX = ThreadLocalRandom.current().nextInt(0, width + 1);
            int potY = ThreadLocalRandom.current().nextInt(0, length + 1);

            while(board[potX][potX] == -1)
            {
                potX = ThreadLocalRandom.current().nextInt(0, width + 1);
                potY = ThreadLocalRandom.current().nextInt(0, length + 1);
            }
            Point mine = new Point(potX, potY);
            board[potX][potY] = -1;
        }
    }

    public ArrayList<Space> getMineCoordinates()
    {
        return mineCoordinates;
    }

    public boolean mineAtPoint(int x, int y)
    {
        if(x < 0 || x > this.width)
        {
            throw new InvalidParameterException("Point is outside grid (x)!");
        }
        if(y < 0 || y > this.length)
        {
            throw new InvalidParameterException("Point is outside grid (y)!");
        }

        Point test = new Point(x, y);
        if(mineCoordinates.contains(test))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
