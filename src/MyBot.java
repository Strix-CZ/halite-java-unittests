import java.util.*;

public class MyBot {

    private GameMap gameMap;
    private final int myId;
    private int round = 0;

    public static void main(String[] args) throws java.io.IOException {
        InitPackage init = Networking.getInit();

        MyBot bot = new MyBot(init);

        Networking.sendInit(bot.getName());

        while(true) {
            GameMap updatedMap = Networking.getFrame();
            List<Move> moves = bot.updateMapAndMakeMove(updatedMap);
            Networking.sendFrame(new ArrayList<>(moves));
        }
    }

    public MyBot(InitPackage init) {
        gameMap = init.map;
        myId = init.myID;
    }

    public String getName() {
        return "Beelzebot 2";
    }

    public List<Move> updateMapAndMakeMove(GameMap updatedMap)
    {
        round++;
        updatedMap(updatedMap);
        return makeMove();
    }

    private void updatedMap(GameMap updatedMap) {
        gameMap = updatedMap;
    }

    private List<Move> makeMove() {
        return Collections.emptyList(); // TODO
    }
}

