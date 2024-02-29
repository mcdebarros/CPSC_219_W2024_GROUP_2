import java.io.*;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Regression {

    public static void main(String[] args) {
        ArrayList<String> dataLines = new ArrayList<>();
        String[] headers = new String[2];
        int order = 1;
        if (args.length != 1) {
            if (args.length < 1) {
                System.out.println("No arguments passed; please specify a filename.");
                System.exit(1);
            } else {
                try {
                    order = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Order given is not an integer!");
                    System.exit(7);
                }
            }
        } else {
            File dataFile = new File(args[0]);
            if (dataFile.exists() && dataFile.canRead() && dataFile.isFile()) {
                try {
                    FileReader readData = new FileReader(dataFile);
                    BufferedReader buffedData = new BufferedReader(readData);
                    String dataLine = buffedData.readLine();
                    int i = 0;
                    while (dataLine != null) {
                        dataLines.add(i,dataLine);
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
                System.exit(4);
            }
        }
        boolean hasHeader = false;
        hasHeader = containsHeaders(dataLines.getFirst());
        Double[][] dataMatrix = new Double[dataLines.size()][2];
        if (!hasHeader) {
            for (int i = 0; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", -1);
                        if (lineContent.length != 2) {
                            System.err.println("Too many data points encountered on line " + (i + 1) + ", check data file and try again!");
                            System.exit(5);
                        } else {
                            dataMatrix[i][0] = Double.parseDouble(lineContent[0]);
                            dataMatrix[i][1] = Double.parseDouble(lineContent[1]);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Something's fishy at line " + (i + 1) + ", check data and try again!");
                        System.exit(6);
                    }
                }
            }
        } else {
            for (int i = 1; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", -1);
                        if (lineContent.length != 2) {
                            System.err.println("Too many data points encountered on line " + (i + 1) + ", check data file and try again!");
                            System.exit(5);
                        } else {
                            dataMatrix[i][0] = Double.parseDouble(lineContent[0]);
                            dataMatrix[i][1] = Double.parseDouble(lineContent[1]);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Something's fishy at line " + (i + 1) + ", check data and try again!");
                        System.exit(6);
                    }
                }
            }
        }
        Double[] a = linear(dataMatrix, order);
    }

    private static Double[] linear(Double[][] data, int order) {
        Double[][] z = new Double[data.length][order + 1];
        Double[][] y = new Double[data.length][1];
        for (int i = 0; i < data.length; i++) {
            y[i][0] = data[i][1];
        }
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < order; j++) {
                z[i][order - j] = Math.pow(data[i][0],(order - j));
            }
        }
        Double[][] zT = transposeMatrix(z);
        Double[][] zTz = multMatrix(zT,z);
        Double[][] zTy = multMatrix(zT,y);
        Double[][] zTInv = invert(zTz);
        Double[][] a =

    }
    private static boolean containsHeaders(String line) {
        String[] parts = line.split("\t");
        for (String part : parts) {
            try {
                Double.parseDouble(part); // Try to parse each part as a double
                return false; // If successful, it's not a header
            } catch (NumberFormatException e) {
                // If parsing fails, it's likely a header
            }
        }
        return true; // If all parts couldn't be parsed as double, it's likely headers
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
    static Double[][] transposeMatrix(Double[][] matrixToTranspose) {
        int y = Array.getLength(matrixToTranspose);
        int x = Array.getLength(matrixToTranspose[0]);
        System.out.println(y + " " + x);
        Double[][] transposedMatrix = new Double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                transposedMatrix[i][j] = matrixToTranspose[j][i];
            }
        }
        return transposedMatrix;
    }

    private static Double[][] multMatrix(Double[][] a, Double[][] b) {
        int rowsA = a.length;
        int rowsB = b.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        Double[][] c = new Double[rowsA][colsB];
        if (colsA != rowsB) {
            System.err.println("Incompatible matrix dimensions!");
            System.exit(8);
        } else {
            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < colsB; j++) {
                    double entry = 0.0;
                    for (int n = 0; n < colsA; n++) {
                        entry += a[i][n] * b[n][j];
                    }
                    c[i][j] = entry;
                }
            }
        }
        return c;
    }

    public static Double[][] invert(Double[][] a) {
        int n = a.length;
        Double[][] x = new Double[n][n];
        Double[][] b = new Double[n][n];
        int[] index = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i][i] = 1.0;
        }
        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < (n - 1); i++)
            for (int j = (i + 1); j < n; j++)
                for (int k = 0; k < n; k++)
                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];

        // Perform backward substitutions
        for (int i = 0; i < n; i++) {
            x[n - 1][i] = b[index[n - 1]][i]/a[index[n - 1]][n - 1];
            for (int j = (n - 2); j >= 0; j--) {
                x[j][i] = b[index[j]][i];
                for (int k = (j + 1); k < n; k++)
                {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    public static void gaussian(Double[][] a, int[] index) {
        int n = index.length;
        Double[] c = new Double[n];

        // Initialize the index
        for (int i = 0; i < n; i++)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; i++) {
            double c1 = 0;
            for (int j = 0; j < n; j++) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1)
                    c1 = c0;
            }
            c[i] = c1;
        }
        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < (n - 1); j++) {
            double pi1 = 0;
            for (int i = j; i < n; i++) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = (j + 1); i < n; i++) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = (j + 1); l < n; l++)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }
}