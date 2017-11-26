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
    private boolean gameOver;

    public GameGrid(int width, int length, int mineCount)
    {
        this.length = length;
        this.width = width;
        board = new Integer[width][length];
        mineCoordinates = new ArrayList<>();
        this.gameOver = false;


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < length; j++)
            {
                board[i][j] = 0;
            }
        }

        // initialize mines into grid
        for(int i = 0; i < mineCount; i++)
        {
            int potX = ThreadLocalRandom.current().nextInt(0, width);
            int potY = ThreadLocalRandom.current().nextInt(0, length );

            while(board[potX][potY] == -1)
            {
                potX = ThreadLocalRandom.current().nextInt(0, width);
                potY = ThreadLocalRandom.current().nextInt(0, length);
            }
            Point mine = new Point(potX, potY);
            mineCoordinates.add(mine);
            board[potX][potY] = -1;
        }

        // Set values for each space
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < length; j++)
            {
                board[i][j] = setSpaceValue(i,j);
            }
        }
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver()
    {
        this.gameOver = true;
    }

    public int getValue(int x, int y)
    {
        if(isValidSpace(x, y))
        {
            return board[x][y];
        }
        return 10;
    }

    public int getLength()
    {
        return length;
    }

    public int getWidth()
    {
        return width;
    }

    public Integer[][] test()
    {
        return board;
    }

    public ArrayList<Point> getMineCoordinates()
    {
        return mineCoordinates;
    }

    public boolean mineAtPoint(int x, int y)
    {
//        if(x < 0 || x > this.width)
//        {
//            throw new InvalidParameterException("Point is outside grid (x)!");
//        }
//        if(y < 0 || y > this.length)
//        {
//            throw new InvalidParameterException("Point is outside grid (y)!");
//        }

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

    private int setSpaceValue(int x, int y)
    {
        // if mine return existing -1 value
        if(board[x][y] < 0)
        {
            return -1;
        }

        int adjacentCount = 0;
        int checkX;
        int checkY;

        // north
        checkX = x + 0;
        checkY = y + 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // northeast
        checkX = x + 1;
        checkY = y + 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // east
        checkX = x + 1;
        checkY = y + 0;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // southeast
        checkX = x + 1;
        checkY = y - 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // south
        checkX = x + 0;
        checkY = y - 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // southwest
        checkX = x - 1;
        checkY = y - 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // west
        checkX = x - 1;
        checkY = y - 0;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }
        // northwest
        checkX = x - 1;
        checkY = y + 1;
        if(isValidSpace(checkX, checkY))
        {
            if(mineAtPoint(checkX, checkY))
            {
                adjacentCount++;
            }
        }

        if(adjacentCount > 8)
        {
            throw new InvalidParameterException("More than 8 mines around check your math");
        }

        return adjacentCount;
    }

    private boolean isValidSpace(int x, int y)
    {
        if(x < 0 || x > this.width)
        {
            return false;
        }
        if(y < 0 || y > this.length)
        {
            return false;
        }
        return true;
    }

}
