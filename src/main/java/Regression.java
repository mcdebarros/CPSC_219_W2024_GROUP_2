import java.lang.reflect.Array;

public class Regression {

    public static void main(String[] args) {
        System.out.print("Team member: Gabriel Ngai");
    }

    private static void linear(String[] args) {
        System.out.println("kellen");

    }

    private static void nonlinear(String[] args) {

    }

    private static void test(String[] args) {

    }

    /**
     * Takes in a matrix and transposes it
     * @param matrixToTranspose is an integer 2D array
     * @return the transposed matrix
     */
    static int[][] transposeMatrix(int[][] matrixToTranspose) {
        int y = Array.getLength(matrixToTranspose);
        int x = Array.getLength(matrixToTranspose[0]);
        System.out.println(y + " " + x);
        int[][] transposedMatrix = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                transposedMatrix[i][j] = matrixToTranspose[j][i];
            }
        }
        return transposedMatrix;
    }

}