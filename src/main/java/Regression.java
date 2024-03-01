//Import useful packages
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import static java.lang.StringTemplate.STR;
import java.util.Scanner;

//Initialize Regression class
public class Regression {

    /**
     * Main body of the function. Assigns passed arguments to variables and coordinates other functions in creating the model
     * @param args String array of length 2; entry 0 should contain the datafile path; entry 1 should contain the desired model order
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Checking input arguments...");
        ArrayList<String> dataLines = new ArrayList<>(); //ArrayList of lines read from the data file
        int order = 1; //Initializes the model order
        if (args.length < 2) { //Terminates the program if not enough arguments are passed
            intro();
            System.exit(1);
        } else {
            try {
                order = Integer.parseInt(args[1]); //Assigns model order based on user argumentsDo
            } catch (NumberFormatException e) { //Terminates program if order passed is not an integer
                intro();
                System.exit(7);
            }
        }
        System.out.println("Successfully interpreted input arguments!\nHandling file...");
        File dataFile = new File(args[0]); //Initializes the data file
        if (dataFile.exists() && dataFile.canRead() && dataFile.isFile()) { //Check if data file exists, is a file, and is readable
            try {
                FileReader readData = new FileReader(dataFile); //Initialize file reader
                BufferedReader buffedData = new BufferedReader(readData); //Initialize buffered reader;
                String dataLine = buffedData.readLine(); //Create a string populated with the contents of the current line
                int i = 0; //Counts the number of data lines present in the file
                while (dataLine != null) { //Continues to read data until end of file
                    dataLines.add(i,dataLine); //Appends the dataLines list with the current value of dataLine
                    dataLine = buffedData.readLine(); //Reads the next line in the file and assigns it to dataLine
                    i++; //Increments the data line counter
                }
            } catch (FileNotFoundException e) { //Terminates the program if specified file not found
                System.err.println("Whoops! Can't find the file. Check and try again.");
                System.exit(3);
            } catch (IOException e) { //Throws input/output exception if one is encountered
                throw new RuntimeException(e);
            }
        } else { //Terminates the program if file is not readable, nonexistent, or not a file
            System.err.println("Cannot read file!");
            System.exit(4);
        }
        System.out.println("Successfully read the data file!\nHandling input data...");
        Double[][] dataMatrix; //Declare the two-dimensional data array
        boolean hasHeader = containsHeaders(dataLines.getFirst()); //Check to see if the data contains headers
        if (!hasHeader) { //Case for no headers
            dataMatrix = new Double[dataLines.size()][2]; //Sizes the data matrix assuming all entries are data
            for (int i = 0; i < dataLines.size(); i++) { //Iterates through all instance of dataLines
                if (!(dataLines.get(i)).isEmpty()) { //Operates only on lines that are not empty
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0); //Creates a 1D array of strings for each data line, separated by (and removing) tabs
                        dataMatrix[i][0] = Double.parseDouble(lineContent[0]); //Assigns X and Y data to its appropriate location in the data matrix
                        dataMatrix[i][1] = Double.parseDouble(lineContent[1]); //Assigns X and Y data to its appropriate location in the data matrix
                        if (dataMatrix[i].length != 2) { //Terminates the program if more than 2 data values are located on a single line
                            System.err.println(STR."Too many data points on line \{i + 1}, check data and try again!");
                            System.exit(12);
                        }
                    } catch (NumberFormatException e) { //Terminates the program if the line contains data not in decimal format
                        System.err.println(STR."Something's fishy at line \{i + 1}, check data and try again!");
                        System.exit(6);
                    }
                }
            }
        } else { //Case for data w/ headers: exactly as above except where specified
            dataMatrix = new Double[dataLines.size() - 1][2]; //Data matrix sized to exclude header line
            for (int i = 1; i < dataLines.size(); i++) { //Loop begins at 1 to exclude header line
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0);
                        dataMatrix[i - 1][0] = Double.parseDouble(lineContent[0]); //Indexed to account for loop starting at i = 1
                        dataMatrix[i - 1][1] = Double.parseDouble(lineContent[1]); //Indexed to account for loop starting at i = 1
                        if (dataMatrix[i - 1].length != 2) { //Indexed to account for loop starting at i = 1
                            System.err.println(STR."Too many data points on line \{i + 1}, check data and try again!");
                            System.exit(12);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(STR."Something's fishy at line \{i + 1}, check data and try again!");
                        System.exit(6);
                    }
                }
            }
        }
        System.out.println("Data extracted!\nGenerating model...");
        List<Object> model = linear(dataMatrix, order); //Retrieve model outputs based on data an order
        Double[][] a = (Double[][]) model.getFirst(); //Assign outputs to respective variables
        Double phi = (Double) model.get(1); //Assign outputs to respective variables
        Double rsq = (Double) model.get(2); //Assign outputs to respective variables
        System.out.println("Model created successfully! Here it is;\n\n");
        for (int i = 0; i < a.length; i++) {
            String coefIndex = STR."a\{i}";
            System.out.println(STR."\{coefIndex}\t\{a[i][0]}");
        }
        System.out.println(STR."\nphi\t\{phi}\nRSQ\t\{rsq}\n\nWould you like to write these parameters to a file? (Y/N)");
        String toWrite = (input.nextLine()).toUpperCase();
        while (!(toWrite.equals("Y") || toWrite.equals("N"))) {
            System.out.println("Oops! I didn't understand your input. Would you like to write to a file? (Y/N)");
            toWrite = (input.nextLine()).toUpperCase(); // Update toWrite inside the loop
        }
        if (toWrite.equals("Y")) {
            File coefficients = new File("coefficients.txt"); //Initialize a txt file to store model outputs
            if (!coefficients.exists()) { //Create a new output file if one does not already exist
                try {
                    coefficients.createNewFile();
                } catch (IOException e) { //Terminate the program if a new file cannot be created
                    System.err.println("Trouble writing to file! Check location and do not interrupt.");
                    System.exit(9);
                }
            }
            if (coefficients.exists() && coefficients.isFile() && coefficients.canWrite()) { //Check file existence, writeability, and file-ness
                try {
                    FileWriter aWrite = new FileWriter(coefficients); //Initialize file writer
                    BufferedWriter aBuffed = new BufferedWriter(aWrite); //Initialized buffered writer
                    for (int i = 0; i < a.length; i++) { //For each coefficient, write a new line containing the order of the coefficient and its value, separated by a tab
                        String coefIndex = STR."a\{i}";
                        aBuffed.write(STR."\{coefIndex}\t\{a[i][0]}\n");
                    }
                    aBuffed.write(STR."\nphi\t\{phi}\nRSQ\t\{rsq}"); //Write the phi and rsq values to the file on their own lines
                    aBuffed.flush(); //Flush the file
                    aBuffed.close(); //Close the file
                    System.out.println("""
                            File written as "coefficients.txt"! See you next time!""");
                } catch (IOException e) { //Terminate the program if the file cannot be written to
                    System.err.println("Oops! Couldn't write to the file.");
                }
            } else { //Terminate the program if the file to write to cannot be created or found
                System.err.println("Cannot access file to write to!");
                System.exit(11);
            }
        } else {
            System.out.println("No problem! Thanks for using our modelling tool!");
        }
    }

    /**
     * Coordinates the production of model output
     * @param data (n by 2) Double array of data values
     * @param order Integer of desired output order
     * @return Object list containing vector of coefficients, phi value, and rsq value
     */
    public static List<Object> linear(Double[][] data, int order) {
        Double[][] z = new Double[data.length][order + 1]; //Initialize the z array (parameters to multiply resultant coefficients by)
        Double[][] y = new Double[data.length][1]; //Initialize the y vector (actual data)
        Double[][] dataAveVec = new Double[data.length][1]; //Initialize "data mean" vector; populated entirely by mean of actual data
        double dataAve = 0.0; //Initialize data mean
        List<Object> model = new ArrayList<>(); //Initialize object list to be populated with model outputs
        for (int i = 0; i < data.length; i++) { //Populate the y vector with provided data
            y[i][0] = data[i][1];
        }
        for (int i = 0; i < data.length; i++) { //Populate the z matrix with appropriate parameters
            for (int j = 0; j < order; j++) {
                z[i][order - j] = Math.pow(data[i][0],(order - j));
            }
        }
        for (int i = 0; i < data.length; i++) { //Populate the first column of the z array with ones; above method does not seem to be able to do this and instead populates w/ null
            z[i][0] = 1.0;
        }
        Double[][] zT = transposeMatrix(z); //Create the transpose of the z matrix
        Double[][] zTz = multMatrix(zT,z); //Multiply the z matrix by its transpose
        Double[][] zTy = multMatrix(zT,y); //Multiply the zT matrix by the y vector
        Double[][] zTzInv = invert(zTz); //Invert the zTz matrix
        Double[][] a = multMatrix(zTzInv,zTy); //Calculate the vector of model coefficients
        Double[][] modelOut = multMatrix(z,a); //Calculate model output based on fit coefficients
        Double[][] model_e = new Double[data.length][1]; //Initialize vector of model residuals
        Double[][] data_e = new Double[data.length][1]; //Initialize vector of data residuals
        double phiMod = 0.0; //Initialize sum of squared residuals for model
        double phiData = 0.0; //Initialize sum of squared residuals for model
        for (int i = 0; i < data.length; i++) {
            dataAve += y[i][0]; //Sum all y values contained in the data
        }
        dataAve = dataAve / data.length; //Calculate the mean of all data y values
        for (int i = 0; i < data.length; i++) {
            dataAveVec[i][0] = dataAve; //Populate the vector of data means
            data_e[i][0] = y[i][0] - dataAveVec[i][0]; //Calculate and record the residual between the data average and the y data
            model_e[i][0] = (y[i][0]) - (modelOut[i][0]); //Calculate and record the residual between the modelled data and the input data
            double squareMod = Math.pow(model_e[i][0],2); //Square the model residual
            double squareData = Math.pow(data_e[i][0],2); //Square the data residual
            phiMod += squareMod; //Increment the sum of squared residuals
            phiData += squareData; //Increment the sum of squared residuals
        }
        Double rsq = (phiData - phiMod) / phiData; //Calculate the R squared value of the produced model
        model.add(a); //Populate the model list with model output
        model.add(phiMod); //Populate the model list with model output
        model.add(rsq); //Populate the model list with model output

        return model; //Return the model output list
    }

    /**
     * Checks to see if the data contains headers
     * @param line String of the first line of the file
     * @return Boolean; true if headers discovered, false if otherwise
     */
    public static boolean containsHeaders(String line) {
        String[] parts = line.split("\t"); // Create a 1D array by splitting the line at the tab
        for (String part : parts) {
            try {
                Double.parseDouble(part); //Attempt to parse each entry as a double
                return false; //Return false if parsing succeeds; this line is not a header
            } catch (NumberFormatException e) {
                //If parsing fails, it's likely that headers were encountered
            }
        }
        return true; //Data likely contains headers if not all parts were parsed as doubles
    }

    /**
     * Displays a program description to the user
     */
    public static void intro() {
        System.out.println("""
                Welcome to our simple linear least squares regression (LLSR) modeler!
                
                This tool functions by drawing on a wealth of matrix operations to fit coefficients to a function that may describe your data.
                To function, it requires a data file; this data file should have two columns (X and Y), with or without headers. Each X and Y
                data value must be in the form of a decimal (0.0, 1.2, 3.0, etc) and the X and Y data on each line should be separated by a tab.
                The path to such a data file should be specified as the first argument when running the program. Additionally, you must specify
                the order of function which you wish to fit to your data, for example, 1 for a 1st order (linear) function, 2 for a quadratic, etc.
                The desired order should be passed as the second argument in the command line. Once the modeller has completed its work, it will
                output a file called "coefficients.txt", which contains the value of all coefficients for your modelled data as well as the sum
                of squared residuals (phi) and R^2 value (RSQ) of the fit. For the moment being, this program is only capable of modelling linear
                systems and producing text-based output; we hope that in future versions, it will handle non-linear systems and produce graphical
                outputs.
                
                If you're seeing this message, it's because you tried to boot the program without passing enough arguments; hopefully you know what
                you need to use this program properly now!""");
    }

    /**
     * Takes in a matrix and transposes it
     * @param matrixToTranspose is an integer 2D array
     * @return the transposed matrix
     */
    public static Double[][] transposeMatrix(Double[][] matrixToTranspose) {
        if (matrixToTranspose.length == 0) return matrixToTranspose; else {
            int y = Array.getLength(matrixToTranspose); //Y dimension of array
            int x = Array.getLength(matrixToTranspose[0]); //X Dimension of array

            Double[][] transposedMatrix = new Double[x][y]; //Initialize transposed matrix

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    transposedMatrix[i][j] = matrixToTranspose[j][i]; //Iterate through the input matrix and flip the indices in the output matrix
                }
            }

            return transposedMatrix;
        }
    }

    /**
     * Multiplies two matrices of dimensions (m,n) and (n,o)
     * @param a 2D array of doubles (m,n)
     * @param b 2D array of doubles (n,o)
     * @return c, the product of a and b
     */
    public static Double[][] multMatrix(Double[][] a, Double[][] b) {
        int rowsA = a.length; //Record matrix dimensions
        int rowsB = b.length; //Record matrix dimensions
        int colsA = a[0].length; //Record matrix dimensions
        int colsB = b[0].length; //Record matrix dimensions
        Double[][] c = new Double[rowsA][colsB]; //Initialize output matrix
        if (colsA != rowsB) { //Check that passed matrices have compatible dimensions
            System.err.println("Incompatible matrix dimensions!");
            System.exit(8);
        } else { //Perform matrix multiplication by the general formula
            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < colsB; j++) {
                    double entry = 0.0; //Initialize the entry to store in c[i][j]
                    for (int n = 0; n < colsA; n++) {
                        entry += a[i][n] * b[n][j]; //Sum the product of matrix members based on dimension n (colsA, rowsB) for some i,j
                    }
                    c[i][j] = entry; //Assign the i,j entry of the c matrix
                }
            }
        }
        return c;
    }

    /**
     * Perform matrix inversion by Gaussian Elimination and LU Decomposition
     * @param a square double array to be inverted
     * @return aInv, the inverted matrix
     * @throws IllegalArgumentException if matrix is singular
     */
    public static Double[][] invert(Double[][] a) {
        int n = a.length; //Establish dimensions of a
        Double[][] aInv = new Double[n][n]; //Initialize inverted matrix
        for (int i = 0; i < n; i++) { //Initialize the inverted matrix as the identity matrix, with 1s along the main diagonal and zeros elsewhere
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    aInv[i][j] = 1.0; //On-diagonal entries
                } else {
                    aInv[i][j] = 0.0; //Off-diagonal entries
                }
            }
        }
        for (int i = 0; i < n; i++) { //Perform Gaussian elimination w/ partial pivoting
            int maxRow = i;
            for (int j = i + 1; j < n; j++) { //Find the pivot row by locating the largest absolute value in some column
                if (Math.abs(a[j][i]) > Math.abs(a[maxRow][i])) {
                    maxRow = j;
                }
            }
            swapRows(a, i, maxRow); //Swap current row with pivot row
            swapRows(aInv, i, maxRow); //Swap current row with pivot row
            Double pivot = a[i][i]; //Scale the row such that the diagonal element is 1
            if (pivot == 0.0) { //Terminate the program if the matrix is singular
                throw new IllegalArgumentException("Matrix is singular");
            }
            for (int j = 0; j < n; j++) {
                a[i][j] /= pivot; //Scale this row in the input matrix
                aInv[i][j] /= pivot; //Update the inverted matrix
            }
            for (int k = 0; k < n; k++) { //Eliminate below the pivot element
                if (k != i) {
                    Double factor = a[k][i];
                    for (int j = 0; j < n; j++) {
                        a[k][j] -= factor * a[i][j]; //Eliminate in the input matrix
                        aInv[k][j] -= factor * aInv[i][j]; //Update the inverted matrix
                    }
                }
            }
        }
        return aInv;
    }

    /**
     * Swap the rows of an array
     * @param a The input matrix
     * @param i Index of the first row
     * @param j Index of the second row
     */
    private static void swapRows(Double[][] a, int i, int j) {
        Double[] temp = a[i]; //Temporarily store a[i]
        a[i] = a[j]; //Assign a[j] to a[i]
        a[j] = temp; //Assign a[i] to a[j]
    }
}