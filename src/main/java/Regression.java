import java.lang.reflect.Array;

public class Regression {


    public static void main(String[] args) {
        displayHelpMessage();
        System.out.print("Team member: Gabriel Ngai");
    }

    private static void linear(String[] args) {
    }

    private static void nonlinear(String[] args) {

    }

    private static void test(String[] args) {

    }

    /**
     * Displays an appropriate help message for the user
     */
    private static void displayHelpMessage() {
        System.out.println("Welcome to the Linear Regression Model.");
        System.out.println("HOW THIS MODEL WORKS:");
        System.out.println("---------------------");
        System.out.println("In principle, this model predicts the value of a dependent variable by analyzing a independent variable.");
        System.out.println("For example, let's say you keep track of how much of your monthly income you spend on groceries from January to June of a year.");
        System.out.println("In this case, your income would be the independent variable and the amount you spend on groceries would be the dependent variable.");
        System.out.println("Now, if you input this data into a linear regression model such as this one, it will estimate a mathematical formula to predict ");
        System.out.println("how much money you will spend on groceries in future months.");
        System.out.println("In short, it will create a line of best fit for your graph.");

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