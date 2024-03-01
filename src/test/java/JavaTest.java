import static org.junit.Assert.*;
import org.junit.Test;
public class JavaTest {
    @Test
    public void testSquareTranspose() { // First tests the transposition of a square matrix.
        Double[][] input = {{1.0, 2.0,3.0},{4.0,5.0,6.0},{7.0,8.0,9.0}}; // Defines an input 3x3 matrix.
        Double[][] expected= {{1.0,4.0,7.0},{2.0,5.0,8.0},{3.0,6.0,9.0}}; // Defines the transposed version of the above.
        assertArrayEquals(expected, Regression.transposeMatrix(input)); // Compares the expected 'test' value matches the one the program calculates.
    }
    @Test
    public void testRectangleTranspose() { // Tests the transposition of a rectangular matrix.
        Double[][] input = {{1.0,2.0,3.0},{4.0,5.0,6.0}}; // First defines an input 3x2 matrix.
        Double[][] expected = {{1.0,4.0},{2.0,5.0},{3.0,6.0}}; // Then the manual transposition of the matrix is listed as a 2x3 matrix.
        assertArrayEquals(expected,Regression.transposeMatrix(input)); // The expected, manual value is then compared to the calculated one.
    }
    @Test
    public void testEmptyTranspose() { // Lastly, tests the transposition of a completely empty matrix.
        Double[][] input = {};
        assertEquals(0, Regression.transposeMatrix(input).length); // Makes sure that the end result of the matrix is still empty.
    }
}
