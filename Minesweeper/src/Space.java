import java.awt.*;

/**
 * Created by keeferbibby on 11/15/17.
 */
public class Space extends Point {

    private int x, y;
    private boolean mine;
    private int number;

    public Space(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.mine = false;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public void setMine(boolean mine)
    {
        this.mine = mine;
    }

    public int getNumber()
    {
        return this.number;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public boolean equals(Space comp)
    {
        if(comp.getX() == this.x && comp.getY() == this.y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
