import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SiteTest {
    private GameMap gameMap;

    @Before
    public void setUp() throws Exception {
        gameMap = GameMapFactory.create(
                "A1- A1- A1-\n" +
                " 2- A1-  4.\n" +
                " 4+ A2- B1.");
    }

    @Test
    public void isUnclaimedTest() throws Exception {
        assertThat(gameMap.getSite(0, 0).isUnclaimed(), is(false));
        assertThat(gameMap.getSite(0, 1).isUnclaimed(), is(true));
    }

    @Test
    public void touchesEnemyTest() throws Exception {
        assertThat(gameMap.getSite(0, 0).touchesEnemy(), is(false));
        assertThat(gameMap.getSite(1, 1).touchesEnemy(), is(false));
        assertThat(gameMap.getSite(2, 0).touchesEnemy(), is(true));
        assertThat(gameMap.getSite(0, 2).touchesEnemy(), is(true));
        assertThat(gameMap.getSite(2, 2).touchesEnemy(), is(true));
    }

    @Test
    public void touchesUnclaimedTest() throws Exception {
        assertThat(gameMap.getSite(0, 0).touchesUnclaimed(), is(true));
        assertThat(gameMap.getSite(1, 1).touchesUnclaimed(), is(true));
        assertThat(gameMap.getSite(1, 0).touchesUnclaimed(), is(false));
        assertThat(gameMap.getSite(2, 2).touchesUnclaimed(), is(true));
    }
}