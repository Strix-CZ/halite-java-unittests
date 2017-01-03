public class Site {
    public static final int NO_OWNER = 0;
    public static final int MAX_STRENGTH = 255;

    private final GameMap map;
    private final Location location;
    private int owner = NO_OWNER;
    private int strength = 10;
    private int production = 1;

    public Site(GameMap map, Location location) {
        this.map = map;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public int getOwner() {
        return owner;
    }

    public boolean isUnclaimed() {
        return owner == NO_OWNER;
    }

    public int getStrength() {
        return strength;
    }

    public int getProduction() {
        return production;
    }

    public Site setOwner(int owner) {
        this.owner = owner;
        return this;
    }

    public Site setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public Site setProduction(int production) {
        this.production = production;
        return this;
    }

    public boolean touchesEnemy() {
        return map.getNeighbours(this)
                .anyMatch(neighbour -> {
                    int neighbourOwner = neighbour.getOwner();
                    return neighbourOwner != NO_OWNER && neighbourOwner != getOwner();
                });
    }

    public boolean touchesUnclaimed() {
        return map.getNeighbours(this).anyMatch(Site::isUnclaimed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        return location.equals(site.location);

    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public String toString() {
        return "S" + location;
    }
}
