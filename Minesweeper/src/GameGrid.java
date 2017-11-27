import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
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

    public GameGrid(int width, int length, int mineCount)
    {
        this.length = length;
        this.width = width;
        this.mineCount = mineCount;
        board = new Integer[width][length];
        mineCoordinates = new ArrayList<>();
        this.gameOver = false;
        this.spaces = new Hashtable<>();


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

    public ArrayList<Space> explore(Space startPoint, Space origin)
    {
        int pointX = startPoint.getxLoc();
        int pointY = startPoint.getyLoc();
        ArrayList<Space> explored = new ArrayList<>();
        int xDir, yDir;

        explored.add(startPoint);
        if(getValue(pointX, pointY) != 0)
        {
            // if space is not a 0 dont explore
            return explored;
        }
        // if point with xDir and yDir modifiers (next space) is valid and exists, explore
        xDir = 1;
        yDir = 0;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY - yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getxLoc() > origin.getxLoc() && nextPoint.getyLoc() == origin.getyLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = -1;
        yDir = 0;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY - yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getxLoc() < origin.getxLoc() && nextPoint.getyLoc() == origin.getyLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = 0;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() > origin.getyLoc() && nextPoint.getxLoc() == origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = 0;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() < origin.getyLoc() && nextPoint.getxLoc() == origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = 1;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() > origin.getyLoc() && nextPoint.getxLoc() > origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = -1;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() < origin.getyLoc() && nextPoint.getxLoc() < origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = 1;
        yDir = -1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() < origin.getyLoc() && nextPoint.getxLoc() > origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }

        xDir = -1;
        yDir = 1;
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY + yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    if(nextPoint.getyLoc() > origin.getyLoc() && nextPoint.getxLoc() < origin.getxLoc())
                    {
                        explored.addAll(explore(nextPoint, startPoint));
                    }
                }
            }
        }


        return explored;

//        explored.add(startPoint);
//        if(getValue(pointX, pointY) != 0)
//        {
//            // if space is not a 0 dont explore
//            return explored;
//        }
//        else
//        {
//            explored.addAll(recExplore(startPoint, 0, 1));
//            explored.addAll(recExplore(startPoint, 1, 1));
//            explored.addAll(recExplore(startPoint, 1, 0));
//            explored.addAll(recExplore(startPoint, 1, -1));
//            explored.addAll(recExplore(startPoint, 0, -1));
//            explored.addAll(recExplore(startPoint, -1, -1));
//            explored.addAll(recExplore(startPoint, -1, 0));
//            explored.addAll(recExplore(startPoint, -1, 1));
//
//
//
//        }
//        return explored;


    }

    private ArrayList<Space> recExplore(Space startPoint, int xDir, int yDir)
    {
        ArrayList<Space> explored = new ArrayList<>();
        if(startPoint == null)
        {
            return explored;
        }

        int pointX = startPoint.getxLoc();
        int pointY = startPoint.getyLoc();

        if(!isValidSpace(pointX, pointY))
        {
            return explored;
        }


        explored.add(startPoint);
        if(getValue(pointX, pointY) != 0)
        {
            // if space is not a 0 dont explore
            return explored;
        }
        // if point with xDir and yDir modifiers (next space) is valid and exists, explore
        if(isValidSpace(pointX + xDir, pointY + yDir))
        {
            if (spaces.get(new Point(pointX + xDir, pointY + yDir)) != null)
            {
                Space nextPoint = spaces.get(new Point(pointX + xDir, pointY - yDir));
                System.out.println(nextPoint.getxLoc() + "," + nextPoint.getyLoc());
                if (isValidSpace(nextPoint.getxLoc(), nextPoint.getyLoc()))
                {
                    explored.addAll(recExplore(nextPoint, xDir, yDir));
                }
            }
        }
        return explored;
    }

}
