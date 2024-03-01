import static org.junit.Assert.*;
import org.junit.Test;
public class JavaTest {
    // Tests to ensure the transposition function works as intended.
    @Test
    public void testSquareTranspose() {
        Double[][] input = {{1.0, 2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}};
        Double[][] expected= {{1.0,4.0,7.0},{2.0,5.0,8.0},{3.0,6.0,9.0}};
        assertArrayEquals(expected, Regression.transposeMatrix(input));
    }
    @Test
    public void testRectangleTranspose() {
        Double[][] input = {{1.0,2.0,3.0},{4.0,5.0,6.0}};
        Double[][] expected = {{1.0,4.0},{2.0,5.0},{3.0,6.0}};
        assertArrayEquals(expected,Regression.transposeMatrix(input));
    }
    @Test
    public void testEmptyTranspose() {
        Double[][] input = {};
        assertEquals(0, Regression.transposeMatrix(input).length);
    }
}
