import no.stelar7.api.l4j8.pojo.match.MatchReference;
import no.stelar7.api.l4j8.pojo.staticdata.champion.StaticChampion;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helpers {

    public static int getChampionFrequency(Summoner summoner, int champion, Map<Integer, StaticChampion> champData)
    {
        int timesPlayed = 0;
        List<MatchReference> matches = summoner.getGames().get();
        List<Integer> championIds = new ArrayList<Integer>();

        for (int i = 1; i <= 10; i++)
        {
            matches.addAll(summoner.getGames().withBeginIndex((long)i*100).get());
            System.out.println("going.");
        }

        String absolutePath = Paths.get("C:\\Users\\Bloop\\Desktop\\Riot API Testing\\src\\main\\resources\\champ_data\\" + summoner.getName()+ ".txt").toString();
        System.out.println(absolutePath);
        try(FileReader fileReader = new FileReader(absolutePath))
        {
            int read = fileReader.read();
            while(read != -1)
            {
                System.out.println((char)read);
                read = fileReader.read();

            }

            /*championIds =;

                for(int i = 1; i <= matches.size(); i++)
                {
                    int championId = championIds.get(i-1);

                    if(championId == champion)
                        timesPlayed++;
                }*/
        }
        catch (FileNotFoundException e)
        {
            System.out.println("does not exist. writing.");
            try (FileWriter fileWriter = new FileWriter(absolutePath)) {

                for(int i = 1; i <= matches.size(); i++)
                {
                    int championId = matches.get(i-1).getChampionId();
                    championIds.add(championId);

                    if(championId == champion)
                        timesPlayed++;
                }

                fileWriter.write(championIds.toString());

            } catch (IOException ee) {
            }
        }
        catch (IOException e) {}

        return timesPlayed;
    }

    public static StaticChampion mostFrequentChamp()
    {
        return null;
    }
}
