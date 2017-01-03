import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class MatrixTest {
    private Matrix<Integer> matrix_1x1;
    private Matrix<Integer> matrix_5x10;

    @Before
    public void setUp() throws Exception {
        matrix_1x1 = new Matrix<>(1, 1);
        matrix_5x10 = new Matrix<>(5, 10);
    }

    @Test
    public void withoutSet_getsNull() throws Exception {
        assertThat(matrix_1x1.get(0, 0), is(nullValue()));
    }

    @Test
    public void setting_shouldGet() throws Exception {
        matrix_5x10.set(0, 0, 1234);
        assertThat(matrix_5x10.get(0, 0), is(1234));

        matrix_5x10.set(4, 0, 1);
        assertThat(matrix_5x10.get(new Location(4, 0)), is(1));
        assertThat(matrix_5x10.get(0, 0), is(1234));

        matrix_5x10.set(0, 0, 4);
        assertThat(matrix_5x10.get(new Site(null, new Location(0 ,0))), is(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCheckRight() throws Exception {
        matrix_5x10.get(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCheckLeft() throws Exception {
        matrix_5x10.get(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCheckUp() throws Exception {
        matrix_5x10.get(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCheckDown() throws Exception {
        matrix_5x10.get(0, 10);
    }
}