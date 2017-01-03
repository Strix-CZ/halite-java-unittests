import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameMapTest {

    private GameMap gameMap;

    @Before
    public void setUp() throws Exception {
        gameMap = GameMapFactory.create(
                "A1- A1- A--\n" +
                "2-- A-- 4..\n" +
                "4++ A-- B..");
    }

    @Test
    public void allSitesTest() throws Exception {
        assertCount(gameMap.getAllSites(), 9);
    }

    @Test
    public void getAllMySitesTest() throws Exception {
        gameMap.getAllMySites(GameMapFactory.MY_ID)
                .forEach(site -> assertThat(site.getOwner(), is(GameMapFactory.MY_ID)));

        assertCount(gameMap.getAllMySites(GameMapFactory.MY_ID), 5);
    }

    @Test
    public void getAllSitesNotMineTest() throws Exception {
        assertCount(gameMap.getAllSitesNotMine(GameMapFactory.MY_ID), 4);
    }

    @Test
    public void getNeighboursTest() throws Exception {
        Set<Site> neighbours = gameMap.getNeighbours(gameMap.getSite(0, 0)).collect(Collectors.toSet());
        assertThat(neighbours, hasItems(
                gameMap.getSite(2, 0),
                gameMap.getSite(1, 0),
                gameMap.getSite(0, 2),
                gameMap.getSite(0, 1)
        ));
        assertThat(neighbours.size(), is(4));
    }

    @Test
    public void getDistanceTest() throws Exception {
        gameMap = new GameMap(5, 5);

        new DistanceTester()
                .from(2, 2)
                    .to(2, 2).shouldBe(0)
                    .to(3, 2).shouldBe(1)
                    .to(2, 3).shouldBe(1)
                    .to(1, 2).shouldBe(1)
                    .to(2, 1).shouldBe(1)
                    .to(3, 3).shouldBe(2)
                    .to(0, 2).shouldBe(2)
                    .to(4, 2).shouldBe(2)
                .from(0, 0)
                    .to(4, 0).shouldBe(1) // wraps around
                    .to(4, 4).shouldBe(2) // wraps around
                    .to(2, 2).shouldBe(4);
    }

    private void assertCount(Stream<Site> siteStream, int expectedCount) {
        int mySitesCount = siteStream.collect(Collectors.toSet()).size();
        assertThat(mySitesCount, is(expectedCount));
    }

    private class DistanceTester {
        private Location origin;
        private Location target;

        public DistanceTester from(int x, int y) {
            origin = new Location(x, y);
            return this;
        }

        public DistanceTester to(int x, int y) {
            target = new Location(x, y);
            return this;
        }

        public DistanceTester shouldBe(int distance) {
            assertThat(gameMap.getDistance(origin, target), is(distance));
            assertThat(gameMap.getDistance(target, origin), is(distance));
            return this;
        }
    }
}