import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.impl.L4J8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPickInfo extends JFrame implements ActionListener {

    JComboBox regionList;
    JTextField usernameBox;
    JLabel askregion;
    JLabel askname;
    JButton okay;
    L4J8 apiIn;
    String usernameIn;
    Platform platform = null;

    GUIPickInfo(L4J8 api)
    {
        setLayout(new GridLayout(3, 2));
        setSize(475,200);
        setTitle("Select Summoner Information");
        String[] regions = { "Region", "NA", "EUW", "EUNE", "TR"};
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        askname = new JLabel();
        askname.setText("What user would you like to search for?");

        usernameBox = new JTextField();
        usernameBox.addActionListener(this);

        askregion = new JLabel();
        askregion.setText("On what region?");

        regionList = new JComboBox(regions);
        regionList.setSelectedIndex(0);
        regionList.addActionListener(this);

        okay = new JButton("Okay");
        okay.addActionListener(this);

        add(askname);
        add(usernameBox);
        add(askregion);
        add(regionList);
        add(okay);

        apiIn = api;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regionList){
            JComboBox cb = (JComboBox)e.getSource();
            String region = (String)cb.getSelectedItem();
            switch(region){
                case "NA": platform = Platform.NA1;
                    break;
                case "EUW": platform = Platform.EUW1;
                    break;
                case "EUNE": platform = Platform.EUN1;
                    break;
                case "TR": platform = Platform.TR1;
                    break;
                default: platform = null;
            }
        }

        if(e.getSource() == okay && platform != null)
        {
            usernameIn = usernameBox.getText();
            GetSummoner.go(usernameIn, platform, apiIn);
        }
    }
}
