# Simple Linear Least Squares Regression Modeler

This Java program implements a simple linear least squares regression (LLSR) modeler. It is designed to fit coefficients to a function that may describe given data, utilizing matrix operations for computations. The program takes in a data file containing two columns (X and Y), either with or without headers, where each X and Y data value is expected to be in decimal format. The path to the data file should be specified as the first argument when running the program, along with the desired order of the regression model as the second argument.

## Functionality

- Reads data from a specified file (with two columns separated by tabs).
- Fits coefficients to a regression model based on the provided data and order.
- Calculates the sum of squared residuals (phi) and the R squared value (RSQ) of the fit.
- Outputs the coefficients, phi, and RSQ to a file named "coefficients.txt".
- Capable of handling linear regression models (for now).

## Usage

1. Compile the Java program: `javac Regression.java`
2. Run the program providing the path to the data file and the desired order of the regression model as command-line arguments: `java Regression <datafile_path> <model_order>`

Example:
```bash
java Regression data.txt 2
```
## Requirements

- JDK21 installed on your system
- An input data file with two columns separated by tabs

## Data Format

Input data can contain headers but does not have to. Each row should contain two entries separated by a tab
```bash
x      y
1.0    2.1
3.2    4.3
5.4    6.5
```
## Output

The program produces 3 model components:

- Phi: The sum of squared residuals for this order of model
- RSQ: The R squared value of the model
- Linear coefficients for the model function
- Option to store in the file "coefficients.txt"

## Future additions

- Support for transcendental and higher order models
- Graphical display of model output relative to data

## Authors

This program was collectively written by Gage, Gabe, Mo, and Kellen
