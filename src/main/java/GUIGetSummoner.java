import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GUIGetSummoner extends JFrame {

    private ImageIcon pfp;
    private JLabel label1;
    private JLabel label2;

    GUIGetSummoner(String pfpLocation, String name)
    {
        setLayout(new GridLayout(0,1));
        setSize(1000,1000);
        setVisible(true);
        setTitle("Get Summoner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            pfp = new ImageIcon(new URL(pfpLocation));
        } catch (MalformedURLException ex)
        {
            System.out.println("Malformed URL Exception");
        }
        label1 = new JLabel(pfp);
        label1.setSize(50,50);
        add(label1);

        label2 = new JLabel(name);
        add(label2);
    }
}
