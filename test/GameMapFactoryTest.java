import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameMapFactoryTest {
    private GameMap map;

    @Test(expected = IllegalArgumentException.class)
    public void emptyMap() throws Exception {
        createMap("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyMap2() throws Exception {
        createMap(
                "\n" +
                "\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void irregularMapTest() throws Exception {
        createMap(
                " \n" +
                "  ");
    }

    @Test(expected = GameMapFactory.UnexpectedCharacterInsteadOfSeparator.class)
    public void wrongSeparatorTest() throws Exception {
        createMap("AAAxBBB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongModifierTest() throws Exception {
        createMap("AAx");
    }

    @Test
    public void unclaimedSpaceMap() throws Exception {
        createMap("   ");

        assertThat(map.width, is(1));
        assertThat(map.height, is(1));

        Site site = map.getSite(0, 0);
        assertThat(site.getOwner(), is(GameMapFactory.DEFAULT_OWNER));
        assertThat(site.getStrength(), is(GameMapFactory.DEFAULT_STRENGTH));
        assertThat(site.getProduction(), is(GameMapFactory.DEFAULT_PRODUCTION));
    }

    @Test
    public void ownersTest() throws Exception {
        createMap(
                "AAA BBB\n" +
                "CCC DDD");

        assertThat(map.width, is(2));
        assertThat(map.height, is(2));

        assertThat(map.getSite(0, 0).getOwner(), is(GameMapFactory.MY_ID));
        assertThat(map.getSite(1, 0).getOwner(), is(GameMapFactory.ENEMY_1));
        assertThat(map.getSite(0, 1).getOwner(), is(GameMapFactory.ENEMY_2));
        assertThat(map.getSite(1, 1).getOwner(), is(GameMapFactory.ENEMY_3));
    }

    @Test
    public void productionsTest() throws Exception {
        createMap("A1. A1- A2+ A3 ");

        assertThat(map.getSite(0, 0).getProduction(), is(GameMapFactory.SMALL_PRODUCTION));
        assertThat(map.getSite(1, 0).getProduction(), is(GameMapFactory.MEDIUM_PRODUCTION));
        assertThat(map.getSite(2, 0).getProduction(), is(GameMapFactory.BIG_PRODUCTION));
        assertThat(map.getSite(3, 0).getProduction(), is(GameMapFactory.DEFAULT_PRODUCTION));
    }

    @Test
    public void strengthsTest() throws Exception {
        createMap(
                "A0. A1-\n" +
                "A2+ A3 \n" +
                "B4  C5 ");

        assertThat(map.getSite(0, 0).getStrength(), is(GameMapFactory.NO_STRENGTH));
        assertThat(map.getSite(1, 0).getStrength(), is(GameMapFactory.SMALL_STRENGTH));
        assertThat(map.getSite(0, 1).getStrength(), is(GameMapFactory.MEDIUM_STRENGTH));
        assertThat(map.getSite(1, 1).getStrength(), is(GameMapFactory.BIG_STRENGTH));
        assertThat(map.getSite(0, 2).getStrength(), is(GameMapFactory.HUGE_STRENGTH));
        assertThat(map.getSite(1, 2).getStrength(), is(GameMapFactory.MAX_STRENGTH));
    }

    private void createMap(String string) {
        map = GameMapFactory.create(string);
    }
}