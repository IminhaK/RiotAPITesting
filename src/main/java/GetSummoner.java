import no.stelar7.api.l4j8.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.l4j8.basic.calling.DataCall;
import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.impl.L4J8;

public class GetSummoner {

    public static void main(String[] args)
    {
        L4J8 api = new L4J8(Secrets.apicred);
        int hours = 24;
        int ttl = hours * 60 * 60 * 1000;
        DataCall.setCacheProvider(new FileSystemCacheProvider(ttl)); //ttl = milliseconds cache stays around

        GUIPickInfo gui = new GUIPickInfo(api);
        gui.setVisible(true);
    }

    public static void go(String user, Platform region, L4J8 api)
    {
        GUIGetSummoner gui = new GUIGetSummoner(user, region, api);
    }
}
