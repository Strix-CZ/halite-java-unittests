import java.util.Arrays;
import java.util.stream.Stream;

public class GameMap {
    private Matrix<Site> contents;
    public final int width;
    public final int height;

    public GameMap(int width_, int height_) {
        width = width_;
        height = height_;
        contents = new Matrix<>(width, height);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Site site = new Site(this, new Location(x, y));
                contents.set(x, y, site);
            }
        }
    }

    public void setOwner(int x, int y, int owner) {
        getSite(x, y).setOwner(owner);
    }

    public void setStrength(int x, int y, int strength) {
        getSite(x, y).setStrength(strength);
    }

    public void setProduction(int x, int y, int production) {
        getSite(x, y).setProduction(production);
    }

    public int getDistance(Location loc1, Location loc2) {
        int dx = Math.abs(loc1.x - loc2.x);
        int dy = Math.abs(loc1.y - loc2.y);

        if (dx > width / 2.0) dx = width - dx;
        if (dy > height / 2.0) dy = height - dy;

        return dx + dy;
    }

    public Site getSite(int x, int y) {
        return contents.get(x, y);
    }

    public Site getSite(Location location) {
        return getSite(location.x, location.y);
    }

    public Stream<Site> getAllSites() {
        return contents.stream();
    }

    public Stream<Site> getAllMySites(int myId) {
        return getAllSites()
                .filter(site -> site.getOwner() == myId);
    }

    public Stream<Site> getAllSitesNotMine(int myId) {
        return getAllSites()
                .filter(site -> site.getOwner() != myId);
    }

    public Stream<Site> getNeighbours(Site site) {
        return Arrays.stream(Direction.DIRECTIONS)
                .map(direction -> direction.from(site, this));
    }
}
