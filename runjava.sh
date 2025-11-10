#!/bin/bash

# * Run this first in terminal
# chmod +x runjava.sh
# ? Now you to run the code, type
# ./runjava.sh

# Compile all .java files into the bin directory
javac -d bin $(find . -name "*.java") || exit 1

# Run the compiled Main class
java -cp bin Main



