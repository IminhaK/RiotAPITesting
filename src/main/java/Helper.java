import no.stelar7.api.l4j8.basic.utils.LazyList;
import no.stelar7.api.l4j8.pojo.match.MatchReference;
import no.stelar7.api.l4j8.pojo.staticdata.champion.StaticChampion;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;

import java.util.*;

public class Helper {

    private static List<Integer> championIds = new ArrayList<>();
    public List<MatchReference> matches;
    public Summoner summoner;

    public Helper(Summoner summonerIn)
    {
        summoner = summonerIn;
        matches = summonerIn.getGames().getLazy();
        System.out.println("Loading fully...");
        ((LazyList<MatchReference>) matches).loadFully();
        System.out.println("Done. Loaded " + matches.size() + " games.");
    }

    public int getChampionFrequency(int champion, Map<Integer, StaticChampion> champData)
    {
        int timesPlayed = 0;
        for(int i = 1; i <= matches.size(); i++)
        {
            int championId = matches.get(i-1).getChampionId();
            championIds.add(championId);

            if(championId == champion)
                timesPlayed++;
        }

        return timesPlayed;
    }

    public int mostFrequentChampAsId()
    {
        Integer[] arr = championIds.toArray(new Integer[championIds.size()]);

        Arrays.sort(arr);

        int count = 1, tempCount;
        int popular = arr[0];
        int temp = 0;
        for (int i = 0; i < (arr.length - 1); i++)
        {
            temp = arr[i];
            tempCount = 0;
            for (int j = 1; j < arr.length; j++)
            {
                if (temp == arr[j])
                    tempCount++;
            }
            if (tempCount > count)
            {
                popular = temp;
                count = tempCount;
            }
        }

        return popular;
    }
}
