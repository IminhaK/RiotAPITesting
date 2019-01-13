import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.basic.constants.types.GameQueueType;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.impl.builders.summoner.SummonerBuilder;
import no.stelar7.api.l4j8.impl.raw.ImageAPI;
import no.stelar7.api.l4j8.pojo.match.*;
import no.stelar7.api.l4j8.pojo.staticdata.champion.StaticChampion;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;

import java.util.*;

public class GetSummoner {

    public static void main(String[] args)
    {
        L4J8 api = new L4J8(Secrets.apicred);

        System.out.println("Who would you like to search?");
        Scanner scanner1 = new Scanner(System.in);
        String username = scanner1.nextLine();

        System.out.println("On what region?");
        Scanner scanner2 = new Scanner(System.in);
        String regionin = scanner2.nextLine();

        if ("na".equals(regionin))
        {
            go(username, Platform.NA1, api);
        } else {
            System.out.println("That is not a valid region. Bye bye!");
        }
    }

    public static void go(String user, Platform region, L4J8 api)
    {
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
        boolean won = match.didWin(self);

        ParticipantIdentity opponentId = match.getLaneOpponentIdentity(self); //get lane opponent id

        boolean hasRoles = match.getGameQueueType() == GameQueueType.NORMAL_5X5_DRAFT || match.getGameQueueType() == GameQueueType.RANKED_SOLO_5X5 || match.getGameQueueType() == GameQueueType.RANKED_FLEX_SR;

        System.out.println("Profile icon: " + pfp);
        System.out.println(name + ", Level " + level);
        System.out.println();
        System.out.format(name + " %s their most recent " + match.getGameQueueType() + " game.", won ? "won" : "lost");
        System.out.println();
        if(opponentId != null && match.getGameQueueType() != GameQueueType.ARAM){

            Participant opponent = match.getParticipantFromParticipantId(opponentId.getParticipantId()); //summs, champ, etc for lane opponent
            StaticChampion opponentChamp = champData.get(opponent.getChampionId());

            System.out.format("They were playing %s" + champion.getName() + " against " + opponentChamp.getName() + ".", hasRoles ? self.getTimeline().getLane() + " " : "");
        }
    }
}
