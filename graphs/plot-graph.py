'''
python3.9 plot-graph.py data/sha512-results-processed.csv testgraph.png sha512 "SHA-512 Parameter-Cost Comparison"
'''

import sys
import pandas as pd
import seaborn as sb
from matplotlib import pyplot as plt

# GLOBAL CONSTANTS
Y_LIMIT = 3000          # no limit: 0 | other limits: 6000 180000
ADD_THRESHOLDS = True   # flag to add red thresholds to graph or not

# Generates the a graph based off of the given csv file with the given title
def createComparisonGraphs(csv_filename, graph_filename, algo, graph_title):
    # opening processed file for data
    try:
        csv_datafile = pd.read_csv(csv_filename)
    except IOError as err:
        print("Unable to open source file {} : {}".format(csv_filename, err), file=sys.stderr)
        sys.exit(-1)

    # Creating figure
    fig = plt.figure()
    if Y_LIMIT != 0:
        plt.ylim([0, Y_LIMIT])   # Set y-axis (time limit) to max of Y_LIMIT
    plt.grid()                   # Add grid lines to plot 
    
    # Plotting graph
    if algo == "sha512" or algo == "bcrypt":
        ax = sb.lineplot(x = "Work Factor", y = "Time", hue = "Device", data = csv_datafile)
    elif algo == "argon2id":
        ax = sb.lineplot(x = "Number of Iterations", y = "Time", hue = "Device", data = csv_datafile)
    if ADD_THRESHOLDS:
        plt.axhline(y=1000, color='r', linestyle='-')       # 1 second threshold line
        plt.axhline(y=500, color='r', linestyle='-')        # 0.5 second threshold line

    plt.title(graph_title)
    fig.savefig(graph_filename, bbox_inches="tight")

def main():
    filename = sys.argv[1]
    output_filename = sys.argv[2]
    algorithm = sys.argv[3]
    title = sys.argv[4]
    createComparisonGraphs(filename, output_filename, algorithm, title)
    
main()