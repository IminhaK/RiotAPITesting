import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.impl.L4J8;

public class GetSummoner {

    public static void main(String[] args)
    {
        L4J8 api = new L4J8(Secrets.apicred);

        GUIPickInfo gui = new GUIPickInfo(api);
        gui.setVisible(true);
    }

    public static void go(String user, Platform region, L4J8 api)
    {
        GUIGetSummoner gui = new GUIGetSummoner(user, region, api);
    }
}
