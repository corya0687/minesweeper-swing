import javax.swing.*;
import java.awt.*;

/**
 * Created by keeferbibby on 11/15/17.
 */
public class Space extends JButton
{
    private int xLoc, yLoc;
    private boolean flagged;

    public Space()
    {
        this.flagged = false;
    }

    public boolean isFlagged()
    {
        return flagged;
    }

    public void setFlagged(boolean flagged)
    {
        this.flagged = flagged;
    }

    public void setLoc(int xLoc, int yLoc)
    {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public int getxLoc()
    {
        return xLoc;
    }

    public int getyLoc()
    {
        return yLoc;
    }
}
