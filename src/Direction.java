import java.util.Random;

public enum Direction {
    HOLD(0, 0),
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    final int dX;
    final int dY;

    Direction(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public static final Direction[] DIRECTIONS = new Direction[]{UP, RIGHT, DOWN, LEFT};
    public static final Direction[] DIRECTIONS_AND_HOLD = new Direction[]{HOLD, UP, RIGHT, DOWN, LEFT};

    private static Direction fromInteger(int value) {
        switch (value) {
            case 0:
                return HOLD;
            case 1:
                return UP;
            case 2:
                return RIGHT;
            case 3:
                return DOWN;
            case 4:
                return LEFT;
            default:
                return null;
        }
    }

    public static Direction randomDirection() {
        return fromInteger(new Random().nextInt(5));
    }

    public Location from(Location origin, GameMap map) {
        int x = (origin.x + dX + map.width) % map.width;
        int y = (origin.y + dY + map.height) % map.height;

        return new Location(x, y);
    }

    public Site from(Site origin, GameMap map) {
        Location newLocation = from(origin.getLocation(), map);
        return map.getSite(newLocation);
    }
}
