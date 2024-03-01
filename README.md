CPSC_219_W2024_GROUP2 - Linear Regression Model

Authors: Kellen Gerla, Mauricio de Barros, Gage Ferguson, Gabriel Ngai

First Demo {

- The user passes two arguments from the command line; the first being the path to a data file, the second being the desired model order
- The program then reads the data file, separates the header from the data, and creates a data matrix of input data 
- The input data is then passed through a series of functions that generate model coefficients that minimize the sum of squared residuals between the observed data and the model output 
- Finally, the coefficients, R2 value, and sum of squared residuals are presented to the user, with the option to write to file.