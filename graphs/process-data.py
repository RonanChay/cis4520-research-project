'''
python3.9 process-data.py data/sha512-results.csv sha512
'''

import sys
import csv
import os

# Global Constants
PROCESSED_FILENAME_SUFFIX = "-results-processed"
SEARCH_PATH = "data/"
FILE_TYPE = ".csv"
ALGORITHMS = ["sha512", "bcrypt", "argon2id"]
CSV_HEADERS = {
    "sha512": "Work Function,Time",
    "bcrypt": "Work Function,Time",
    "argon2id": "Number of Iterations,Memory Limit,Time"
}

# Processes the source data file to prepare it for graphs
def processCostData(hardware, algorithm):
    source_data_filenames = []
    source_filePtrs = []
    source_fileReaders = []
    # Finding and creating full file path of source data files
    folder_path = SEARCH_PATH + hardware + "/"
    for filename in os.listdir(path=folder_path):
        if algorithm in filename:
           source_data_filenames.append(folder_path + filename)
           
    print("filenames: ", source_data_filenames)
            
    # Open source data files
    try:
        for filename in source_data_filenames:
            source_filePtrs.append(open(filename, encoding="utf-8-sig"))
    except IOError as err:
        print("Unable to open file '{}' : {}".format(
                filename, err), file=sys.stderr)
        sys.exit(-2)
    for filePtr in source_filePtrs:
        source_fileReaders.append(csv.reader(filePtr))

    # Initializing final processed file with headers
    processed_full_filename = folder_path + "processed/" + algorithm + PROCESSED_FILENAME_SUFFIX + FILE_TYPE
    processed_fileWriter = open(processed_full_filename, "w")
    print(CSV_HEADERS[algorithm], file = processed_fileWriter)

    # Calculating time averages
    file_number = 0
    work_factors = []
    times = []
    num_iterations = []
    mem_limit = []
    for fileReader in source_fileReaders:
        file_number += 1
        count = 0
        for cost_row_data in fileReader:
            if cost_row_data[0] == "WORK_FACTOR" or cost_row_data[0] == "NUM_ITERATIONS":
                # Ignore header from source data file
                continue
            
            if algorithm == "sha512":
                if file_number == 1:
                    work_factors.append(int(cost_row_data[0]))
                    times.append(int(cost_row_data[2]))
                else:
                    times[count] += int(cost_row_data[2])
            elif algorithm == "bcrypt":
                if file_number == 1:
                    work_factors.append(int(cost_row_data[0]))
                    times.append(int(cost_row_data[3]))
                else:
                    times[count] += int(cost_row_data[3])
            elif algorithm == "argon2id":
                if file_number == 1:
                    num_iterations.append(int(cost_row_data[0]))
                    mem_limit.append(int(cost_row_data[1]))
                    times.append(int(cost_row_data[6]))
                else:
                    times[count] += int(cost_row_data[6])
            count += 1
        # print(work_factors, times, num_iterations, mem_limit)
    
    for i in range(len(times)):
        if algorithm == "sha512" or algorithm == "bcrypt":
            print("{},{}".format(work_factors[i], times[i]/file_number), file = processed_fileWriter)
        elif algorithm == "argon2id":
            print("{},{}KiB,{}".format(num_iterations[i], mem_limit[i], times[i]/file_number), file = processed_fileWriter)
        

    processed_fileWriter.close()
    for filePtr in source_filePtrs:
        filePtr.close()

def main():
    if len(sys.argv) != 3:
        print("Invalid command-line arguments: Ensure follows format <hardware> <algorithm>", file=sys.stderr)
        sys.exit(-1)
    hardware = sys.argv[1].lower()
    algorithm = sys.argv[2].lower()
    if algorithm not in ALGORITHMS:
        print("Invalid algorithm input: must be either \"sha512\", \"bcrypt\", or \"argon2id\"")
    processCostData(hardware, algorithm)

main()