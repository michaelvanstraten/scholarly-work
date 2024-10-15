#!/bin/bash

# Check if the required number of arguments is provided
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 input_pdf output_prefix"
    exit 1
fi

infile=$1
outputprefix=$2
splitinfo="${infile%.*}.splitinfo"

# Check if the split info file exists
if [ ! -e "$splitinfo" ]; then
    echo "Error: Split info file '$splitinfo' not found."
    exit 1
fi

# Read the split info file and process each problem
while read -r problem start_page end_page; do
    output_file="${outputprefix}_Problem${problem}.pdf"
    pdfjam "$infile" "${start_page}-${end_page}" -o "$output_file" >/dev/null 2>&1
    echo "Created: $output_file"
done < "$splitinfo"
