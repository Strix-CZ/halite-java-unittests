import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Honza on 03.01.2017.
 */
public class UtilTest {
    @Test
    public void equalLengthStringsTest() throws Exception {
        assertThat(Util.getStringsHaveEqualLength(new String[] {}), is(true));
        assertThat(Util.getStringsHaveEqualLength(new String[] {"", ""}), is(true));
        assertThat(Util.getStringsHaveEqualLength(new String[] {"a", "b", "c"}), is(true));
        assertThat(Util.getStringsHaveEqualLength(new String[] {"a", ""}), is(false));
        assertThat(Util.getStringsHaveEqualLength(new String[] {"a", "a", "aa"}), is(false));
    }
}