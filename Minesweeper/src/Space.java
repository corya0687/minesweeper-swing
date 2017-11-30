import javax.swing.*;
import java.awt.*;

/**
 * Created by keeferbibby on 11/15/17.
 */
public class Space extends JPanel
{
    private int xLoc, yLoc, value;
    private boolean flagged;
    private boolean labelText;
    private JLabel label;

    public Space()
    {
        setLayout(new BorderLayout());
        this.flagged = false;
        this.label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }

    public void setLabelText(String label)
    {
        this.label.setText(label);
        this.label.setVisible(true);
        add(this.label, BorderLayout.CENTER);
    }

    public void setTextFont(Font font)
    {
        this.label.setFont(font);
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

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getxLoc()
    {
        return xLoc;
    }

    public int getyLoc()
    {
        return yLoc;
    }

    public void setColor(int value)
    {
        switch (value)
        {
            case 1:
                label.setForeground(new Color(0, 0, 200));
                break;
            case 2:
                label.setForeground(new Color(0,100, 0));
                break;
            case 3:
                label.setForeground(new Color(200, 0, 0));
                break;
            case 4:
                label.setForeground(new Color(0, 0, 100));
                break;
            case 5:
                label.setForeground(new Color(100, 0, 0));
                break;
            case 6:
                label.setForeground(new Color(0, 100, 100));
                break;
            case 7:
                label.setForeground(new Color(100, 0, 100));
                break;
            case 8:
                label.setForeground(new Color(0, 0, 0));
                break;
            default:
                label.setForeground(new Color(0, 0, 0));
                break;
        }
    }
}
