import java.io.*;
import java.lang.reflect.Array;
import java.util.Scanner;

public class Regression {

    public static void main(String[] args) {
        if (args.length != 1) {
            if (args.length < 1) {
                System.out.println("No arguments passed; please specify a filename.");
                System.exit(1);
            } else {
                System.out.println("Too many arguments! Pass only 1.");
                System.exit(2);
            }
        } else {
            File dataFile = new File(args[0]);
            String[] dataLines = new String[1];
            if (dataFile.exists() && dataFile.canRead() && dataFile.isFile()) {
                try {
                    FileReader readData = new FileReader(dataFile);
                    BufferedReader buffedData = new BufferedReader(readData);
                    String dataLine = buffedData.readLine();
                    int i = 0;
                    while (dataLine != null) {
                        dataLines[i] = dataLine;
                        dataLine = buffedData.readLine();
                        i++; //Better way?
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Whoops! Can't find the file. Check and try again.");
                    System.exit(3); //Come back to this later; Need better exceptions.
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.err.println("Cannot read file!");
            }
        }

    }
    private static void linear(String[] args) {

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