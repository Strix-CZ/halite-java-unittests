import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DirectionTest {
    private GameMap gameMap = new GameMap(10, 10);

    @Test
    public void noWrapTest() throws Exception {
        fromLocation(5, 5)
                .to(Direction.UP).shouldBe(5, 4)
                .to(Direction.DOWN).shouldBe(5, 6)
                .to(Direction.LEFT).shouldBe(4, 5)
                .to(Direction.RIGHT).shouldBe(6, 5)
                .to(Direction.HOLD).shouldBe(5, 5);
    }

    @Test
    public void wrapTest() throws Exception {
        fromLocation(0, 0)
                .to(Direction.UP).shouldBe(0, 9)
                .to(Direction.LEFT).shouldBe(9, 0);

        fromLocation(9, 9)
                .to(Direction.DOWN).shouldBe(9, 0)
                .to(Direction.RIGHT).shouldBe(0, 9);
    }

    private FromTester fromLocation(int x, int y) {
        return new FromTester(new Location(x, y));
    }

    private class FromTester {
        private Location origin = null;
        private Direction direction = null;

        public FromTester(Location origin) {
            this.origin = origin;
        }

        public FromTester to(Direction direction) {
            this.direction = direction;
            return this;
        }

        public FromTester shouldBe(int x, int y) {
            Location expectedTarget = new Location(x, y);
            assertThat(direction.from(origin, gameMap), is(expectedTarget));

            Site originSite = gameMap.getSite(origin);
            Site expectedSite = gameMap.getSite(expectedTarget);
            assertThat(direction.from(originSite, gameMap), is(expectedSite));

            return this;
        }
    }
}