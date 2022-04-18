# Mechanical Petőfi

A text editor with autocomplete and word suggestion, based on Markov chains.

## Building

The code is packaged as a standard Maven project, meaning you can do:

`mvn clean install`

This will output an executable JAR in the target directory.

## Running

Run the executable JAR from the terminal of your choosing:

`java -jar target/mechanical-petofi-*.jar`

> NOTE: On Windows, you'll need to use `javaw`

## Usage

At first glance, the application will present a list of known poets. Upon choosing one, it will process the appropriate
text file and construct a Markov chain based on its contents.

From the editor - aside from typing - the following features are available:

* Autocomplete
  * Press <kbd>TAB</kbd> in the middle of a word
  * The editor will find the most likely match for the word fragment
* Autocontinue
  * Press <kbd>TAB</kbd> after putting a whitespace after the last word
  * The editor will choose a random word, considering how likely each word is after the current one
* Word suggestion
  * During typing, the editor will suggest the most likely words to continue with, on the bottom bar

At any point, press <kbd>CTRL</kbd>-<kbd>C</kbd> to exit.
