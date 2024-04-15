'''
Run command: python3 process-data.py <hardware> <algorithm>
'''

import sys
import csv
import os

# Global Constants
PROCESSED_FILENAME_SUFFIX = "-results-processed"
SEARCH_PATH = "data/"
FILE_TYPE = ".csv"
ALGORITHMS = ["sha512", "bcrypt", "argon2id"]   # PHAs accepted as input
HARDWARE = ["pc", "android"]        # Hardware devices that are accepted as input
CSV_HEADERS = {
    "sha512": "Work Factor,Time",
    "bcrypt": "Work Factor,Time",
    "argon2id": "Number of Iterations,Memory Limit,Time"
}

# Processes the source data files to prepare for graphs
def processCostData(hardware, algorithm):
    source_data_filenames = []
    source_filePtrs = []
    source_fileReaders = []
    # Finding and creating full file path of source data files
    folder_path = SEARCH_PATH + hardware + "/"
    for filename in os.listdir(path=folder_path):
        if algorithm in filename:
           source_data_filenames.append(folder_path + filename)
           
    print("CSV files to be processed: ", source_data_filenames)
            
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
    file_number = 0     # Count of files processed
    work_factors = []   # List of work factors (for BCrypt and SHA-512)
    sum_times = []      # List of sum of times
    num_iterations = [] # List of number of iterations (for Argon2id)
    mem_limit = []      # List of memory limits (for Argon2id)
    # Process every csv file found
    for fileReader in source_fileReaders:
        file_number += 1
        count = 0   # row counter
        # Process the time data for each row in a csv file
        for cost_row_data in fileReader:
            if cost_row_data[0] == "WORK_FACTOR" or cost_row_data[0] == "NUM_ITERATIONS":
                # Ignore header from source data file
                continue
            
            # SHA-512 file processing
            if algorithm == "sha512":
                if file_number == 1:
                    # Initialise the lists
                    work_factors.append(int(cost_row_data[0]))
                    sum_times.append(int(cost_row_data[2]))
                else:
                    # Sum the times
                    sum_times[count] += int(cost_row_data[2])
                    
            # BCrypt file processing
            elif algorithm == "bcrypt":
                if file_number == 1:
                    # Initialise the lists
                    work_factors.append(int(cost_row_data[0]))
                    sum_times.append(int(cost_row_data[3]))
                else:
                    # Sum the times
                    sum_times[count] += int(cost_row_data[3])
                    
            # Argon2id file processing
            elif algorithm == "argon2id":
                if file_number == 1:
                    # Initialise the lists
                    num_iterations.append(int(cost_row_data[0]))
                    mem_limit.append(int(cost_row_data[1]))
                    sum_times.append(int(cost_row_data[6]))
                else:
                    # Sum the times
                    sum_times[count] += int(cost_row_data[6])
            count += 1
    
    # Write the parameters and respective average times to the final processed csv file
    for i in range(len(sum_times)):
        if algorithm == "sha512" or algorithm == "bcrypt":
            print("{},{}".format(work_factors[i], sum_times[i]/file_number), file = processed_fileWriter)
        elif algorithm == "argon2id":
            print("{},{}KiB,{}".format(num_iterations[i], mem_limit[i], sum_times[i]/file_number), file = processed_fileWriter)
            
    print("Processed data successfully! See file at " + processed_full_filename)
        
    # Close file pointers
    processed_fileWriter.close()
    for filePtr in source_filePtrs:
        filePtr.close()

def main():
    if len(sys.argv) != 3:
        print("Invalid command-line arguments: Ensure follows format <hardware> <algorithm>", file=sys.stderr)
        sys.exit(-1)
        
    hardware = sys.argv[1].lower()
    algorithm = sys.argv[2].lower()
    if hardware not in HARDWARE:
        print("Invalid hardware input: must be either \"pc\" or \"android\"")
        sys.exit(-2)
    if algorithm not in ALGORITHMS:
        print("Invalid algorithm input: must be \"sha512\", \"bcrypt\", or \"argon2id\"")
        sys.exit(-3)
        
    processCostData(hardware, algorithm)

main()