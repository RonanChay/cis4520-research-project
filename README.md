# CIS 4520 Research Project - PHAs

This project is the code implementation for my CIS*4520 research project for
Winter 2024. I implement the SHA-512, BCrypt, and Argon2id algorithms using
Java Security package and the BouncyCastle cryptography library to analyze the
relationship between the parameter inputs of a PHA on the cost to hash a password, and the 
impact of hardware on the cost. 

The application is terminal-based and uses a menu with clear prompts to guide
the user on how to navigate the program.

## Dependencies

You will need to have the following installed before running the program:
1. Java 18+
2. Gradle v7.5.1+
3. Python v3.9+ - For graphing scripts
   1. Seaborn
   2. Matplotlib
   3. Pandas
   4. CSV

## PHA Implementation and Data Generation

SHA-512 was implemented using the Java Security package, and BCrypt and Argon2id
was implemented using the BouncyCastle cryptography library. More details can
be found in the written paper.

Below is the general file structure for the project:
```
app/
├─ src/main/java/code/
│  ├─ algorithms/
│  │  ├─ Algorithm.java
│  │  ├─ Argon2idAlgo.java
│  │  ├─ BcryptAlgo.java
│  │  ├─ SHA512Algo.java
│  ├─ ResearchProject.java
│  ├─ ResultGenerator.java
│  ├─ Utils.java
├─ build.gradle
```
The ```algorithms``` package contains the PHA implementations. ```ResearchProject.java```
is the main class and takes care of the terminal-based output of the application.
```ResultGenerator.java``` contains methods to automatically generate the 
parameter-cost comparison data for each algorithm. ```Utils.java``` has some helper
methods.

### Compiling and Running
The application was built with Java 18 and Gradle v7.5.1.
To install any dependencies and build the program, run the command below:
```
gradle clean build
```
To run the program, use this command:
```
gradle --console plain run
```
You can also do it all at once with the following command instead:
```
gradle --console plain clean build run
```
A terminal-based menu will be displayed once the program runs. The user can then
follow the prompts shown to select their desired choice.

Note that only one option can be selected at a time and the program will exit 
once that option is complete. Simply re-run the application again if you wish
to choose a different option or if something goes wrong.

## Graphing with Python

In the ```graphs``` directory in the root directory, there are the raw csv data files
for each device for each algorithm from the Java program, as well as the processed 
csv data. Also included are some simple Python scripts that use Seaborn, Matplotlib,
and CSV libraries to generate the graphs needed to compare and analyse the data for 
each algorithm. More details can be found in the written paper.

Below is the file structure for the raw and processed data and Python scripts:
```
graphs/
├─ data/
│  ├─ android/
│  │  ├─ processed/
│  ├─ pc/
│  │  ├─ processed/
│  ├─ argon2id-results-processed-both.csv
│  ├─ bcrypt-results-processed-both.csv
│  ├─ sha512-results-processed-both.csv
├─ plot-graph.py
├─ process-data.py
```
The `data` directory contains the all the csv files for each algorithm
for both devices. `android` and `pc` directories contain the csv files with raw
data that was output from the result generator mentioned above. The two `processed`
directories contain the csv file output of the `process-data.py` script with the 
processed data for each device respectively.

`process-data.py` is a Python script that uses CSV to read all the raw data files for 
a given algorithm and hardware device, calculate the average times for each parameter 
combination, and output a processed csv file that contains only the necessary data to 
create the graphs. These output csv files are stored in the respective `processed` directory
with the name `<algorithm>-results-processed.csv` where `<algorithm>` is the
name of the algorithm.

The two csv files with the processed data for each algorithm was manually combined to make
one csv file with the processed data for each algorithm with both device data. These are in
the `data` directory and are called `<algorithm>-results-processed-both.csv`

`plot-graph.py` is another python script that uses Seaborn, Matplotlib and Pandas to read
the processed csv data for both devices for each algorithm and plots the relevant graphs. These
graphs are output in the `data` directory as well. Some flags can be tweaked by changing the
constants/flags in the scripts:
1. `Y_LIMIT`: the maximum y-value to set the scale of the y-axis (set to 0 for automatic limit)
2. `ADD_THRESHOLDS`: boolean flag to add red thresholds to the graph (True) or not (False)

### Running the scripts

`/!\ `  Ensure that all the directories are structured correctly and the relevant data
files are in the correct locations. Also ensure your terminal is in the `graphs` directory
before running the scripts

To run the `process-data.py` script to process the raw data, run the following command:
```
python3 process-data.py <hardware> <algorithm>
```
`<hardware>` is the hardware device to process and can either be `pc` or `android`.

`<algorithm>` is the algorithm to process and can be `sha512`, `bcrypt`, or `argon2id`.

To run the `plot-graph.py` script to generate the graphs, run the following command:
```
python3 plot-graph.py <datafile path> <algorithm> <graph title> <output filename>
```
`<datafile path>` is the relative path of the data file to be graphed. Usually should be 
 `data/<algorithm>-results-processed-both.csv`.

`<algorithm>` is the algorithm to process and can be `sha512`, `bcrypt`, or `argon2id`.

`<graph title>` is the title of the graph that the user chooses. It should be within `""`
if there are spaces in the title. (For eg: `"SHA-512 Parameter-Cost Comparison"`)

`<output filename>` is the name of the output file for the graph. Specify the file type in the
filename as well (For eg: `testgrapgh.png` will make the graph into a png file called 
`testgraph.png`)