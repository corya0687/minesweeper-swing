import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by keeferbibby on 11/7/17.
 */
public class GameGrid extends JPanel {

    private Integer[][] board;
    private ArrayList<Point> mineCoordinates;
    private int length, width, mineCount;
    private boolean gameOver;
    private Hashtable<Point, Space> spaces;
    private HashSet<Space> explored;


    public GameGrid(int width, int length, int mineCount)
    {
        this.length = length;
        this.width = width;
        this.mineCount = mineCount;
        board = new Integer[width][length];
        mineCoordinates = new ArrayList<>();
        this.gameOver = false;
        this.spaces = new Hashtable<>();
        this.explored = new HashSet<>();


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < length; j++)
            {
                board[i][j] = 0;
            }
        }

    }

    public void addSpace(Point p, Space space)
    {
        spaces.put(p, space);
    }

    public Hashtable<Point, Space> getSpaces()
    {
        return spaces;
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
        if(x < 0 || x >= this.width)
        {
            return false;
        }
        if(y < 0 || y >= this.length)
        {
            return false;
        }
        return true;
    }

    // Randomly generates mines while giving the user a safe start of a 3x3 grid around where they click
    public void generateMines(Point startPoint)
    {
        int startX = (int) startPoint.getX();
        int startY = (int) startPoint.getY();

        // initialize mines into grid
        for(int i = 0; i < this.mineCount; i++)
        {
            int potX = ThreadLocalRandom.current().nextInt(0, width);
            int potY = ThreadLocalRandom.current().nextInt(0, length );

            while(board[potX][potY] == -1 || inSafeSpace(startX, startY, potX, potY))
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

    private boolean inSafeSpace(int startX, int startY, int x, int y)
    {
        if(Math.abs(startX - x) < 2 && Math.abs(startY - y) < 2)
        {
            return true;
        }
        return false;
    }

    public HashSet<Space> explore(Space startPoint)
    {
        int pointX = startPoint.getxLoc();
        int pointY = startPoint.getyLoc();
        int xDir, yDir;

        if(explored.contains(startPoint))
        {
            return explored;
        }
        else
        {
            explored.add(startPoint);
        }

        if(getValue(pointX, pointY) != 0)
        {
            // if space is not a 0 dont explore
            return explored;
        }

        xDir = 1;
        yDir = 0;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = -1;
        yDir = 0;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = 0;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = 0;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = 1;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = -1;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = -1;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        xDir = 1;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if(spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                explored.addAll(explore(nextPoint));
            }
        }

        return explored;
    }

}
