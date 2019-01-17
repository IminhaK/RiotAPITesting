import no.stelar7.api.l4j8.pojo.match.MatchReference;
import no.stelar7.api.l4j8.pojo.staticdata.champion.StaticChampion;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Helpers {

    private static List<Integer> championIds = new ArrayList<>();
    private static List<String> championIdsString = new ArrayList<>();

    public static int getChampionFrequency(Summoner summoner, int champion, Map<Integer, StaticChampion> champData)
    {
        int timesPlayed = 0;
        List<MatchReference> matches = summoner.getGames().get();

        String absolutePath = Paths.get("C:\\Users\\Bloop\\Desktop\\Riot API Projects\\Riot API Testing\\src\\main\\resources\\champ_data\\" + summoner.getName()+ ".txt").toString();
        try(FileReader fileReader = new FileReader(absolutePath))
        {
            File f = new File(absolutePath);
            Scanner s = new Scanner(f);
            String pattern = "\\W";
            String scan;

            while(s.hasNext())
            {
                scan = s.next().replaceAll(pattern, "");
                championIdsString.add(scan);
            }

            for(String string : championIdsString) championIds.add(Integer.valueOf(string));

            for(int i = 1; i <= championIds.size(); i++)
            {
                int championId = championIds.get(i-1);

                if(championId == champion)
                    timesPlayed++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File for " + summoner.getName() + " does not exist. Writing.");
            try (FileWriter fileWriter = new FileWriter(absolutePath)) {

                for (int i = 1; i <= 9999; i++)
                {
                    List<MatchReference> matchref = summoner.getGames().withBeginIndex((long)i*100).get();
                    matches.addAll(matchref);
                    System.out.println(i*100 + " games analyzed so far.");
                    if(matchref.isEmpty()) break;
                }

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

    public static int mostFrequentChampAsId()
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
