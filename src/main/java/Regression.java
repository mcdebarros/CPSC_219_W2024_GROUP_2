import java.io.*;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class Regression {

    public static void main(String[] args) {
        ArrayList<String> dataLines = new ArrayList<>();
        String[] headers = new String[2];
        int order;
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
    }

    private static void linear(Double[][] data, int order) {
        Double[][] z = new Double[data[0].length][order + 1];
        Double[] y = data[1];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < order; j++) {
                z[i][order - j] = Math.pow(data[i][0],(order - j));
            }
        }
        Double[][] zT = transposeMatrix(z);
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

}