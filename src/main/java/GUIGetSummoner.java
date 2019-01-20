import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.basic.constants.types.GameQueueType;
import no.stelar7.api.l4j8.basic.constants.types.LaneType;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.impl.builders.summoner.SummonerBuilder;
import no.stelar7.api.l4j8.impl.raw.ImageAPI;
import no.stelar7.api.l4j8.pojo.match.*;
import no.stelar7.api.l4j8.pojo.staticdata.champion.StaticChampion;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GUIGetSummoner extends JFrame {

    private ImageIcon pfpimage;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;

    GUIGetSummoner(String user, Platform region, L4J8 api)
    {
        setLayout(new GridLayout(0,1));
        setSize(500,250);
        setVisible(true);
        setTitle("Get Summoner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Summoner summoner = new SummonerBuilder().withPlatform(region).withName(user).get();
        Map<Integer, StaticChampion> champData = api.getDDragonAPI().getChampions();
        //pfp
        String pfp = ImageAPI.getInstance().getProfileIcon(region, user);
        //name and lv
        Integer level = summoner.getSummonerLevel();
        String name = summoner.getName();
        //most recent game
        List<MatchReference> matches = summoner.getGames().get();
        MatchReference recentGame = matches.stream().max(Comparator.comparing(MatchReference::getTimestamp)).get();
        Match match = recentGame.getFullMatch();

        Participant self = match.getParticipantFromSummonerId(summoner.getSummonerId()); //game data for user (summs, champ etc)
        StaticChampion champion = champData.get(recentGame.getChampionId());
        MatchPerks summs = self.getPerks();
        boolean win = match.didWin(self);
        String won = win ? "won" : "lost";

        ParticipantIdentity opponentId = match.getLaneOpponentIdentity(self); //get lane opponent id

        boolean hasRoles = match.getGameQueueType() == GameQueueType.NORMAL_5X5_DRAFT || match.getGameQueueType() == GameQueueType.RANKED_SOLO_5X5 || match.getGameQueueType() == GameQueueType.RANKED_FLEX_SR;

        try {
            pfpimage = new ImageIcon(new URL(pfp));
        } catch (MalformedURLException ex)
        {
            System.out.println("Malformed URL Exception");
        }
        label1 = new JLabel(pfpimage);
        label1.setBounds(0,0,500,500);
        add(label1);

        label2 = new JLabel(name + ", Level " + level);
        add(label2);

        label3 = new JLabel(name + " " + won + " their most recent " + match.getGameQueueType() + " game as " + champion.getName() + ".");
        add(label3);

        if(opponentId != null && match.getGameQueueType() != GameQueueType.ARAM){

            Participant opponent = match.getParticipantFromParticipantId(opponentId.getParticipantId()); //summs, champ, etc for lane opponent
            StaticChampion opponentChamp = champData.get(opponent.getChampionId());
            LaneType role = self.getTimeline().getLane();

            if(hasRoles)
                label4 = new JLabel("They were playing " + role + " against " + opponentChamp.getName() + ".");
            else
                label4 = new JLabel("They were playing against " + opponentChamp.getName() + ".");
            add(label4);
        }

        Helper helper = new Helper(summoner);

        label5 = new JLabel(name + " has played " + champion.getName() + " " + helper.getChampionFrequency(champion.getId(), champData) + " times.");
        //label5 = new JLabel(name + " has played " + champData.get(64).getName() + " " + Helper.getChampionFrequency(summoner, 64, champData) + " times."); //FOR TESTING A SPECIFIC CHAMPION
        add(label5);

        label6 = new JLabel("Their most popular champion is " + champData.get(helper.mostFrequentChampAsId()).getName());
        add(label6);
    }
}
