# bulk-download
######Bulk download documents from url guessing extension if necessary######

Simple process that executes in the directory where the input .tsv file is located and:

1. Reads from a source .tsv file with two columns: documentId, documentExtension 
2. Downloads the document from a url formatted like: urlRoot + documentId
3. Guesses the extension of the document if not informed
4. Saves the the downloaded document to a target directory
5. Generates logs with output: error, info and console

**call syntax:** java Downloader fileToRead urlRoot

**example:** java Downloader Desktop/test/test.tsv http://www.google.com?image=

**NOTE:** fileToRead path is realtive to environment variable: user.home
