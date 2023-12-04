#!/bin/bash

# Check if the required number of arguments is provided
if [ "$#" -ne 2 ]; then
	echo "Usage: $0 input_pdf output_prefix"
	exit 1
fi

infile=$1
outputprefix=$2

# Check if the input PDF file exists
if [ ! -e "$infile" ]; then
	echo "Error: Input PDF file '$infile' not found."
	exit 1
fi

# Extract page numbers from bookmarks
pagenumbers=($(pdftk "$infile" dump_data |
	grep '^BookmarkPageNumber: ' | cut -f2 -d' ' | uniq)
end)

# Iterate through the page numbers and create individual PDFs
for ((i = 0; i < ${#pagenumbers[@]} - 1; ++i)); do
	start_page=${pagenumbers[i]}
	end_page=${pagenumbers[i + 1]}

	# If end_page is 'end', set it to the last page number
	[ "$end_page" = "end" ] && end_page=$(pdftk "$infile" dump_data | grep '^NumberOfPages: ' | cut -f2 -d' ')

	output_file="${outputprefix}_${start_page}-${end_page}.pdf"

	# Concatenate pages using pdftk
	pdftk "$infile" cat "$start_page-$end_page" output "$output_file"

	echo "Created: $output_file"
done
