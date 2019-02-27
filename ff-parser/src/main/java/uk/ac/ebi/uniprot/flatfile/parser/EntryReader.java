package uk.ac.ebi.uniprot.flatfile.parser;

import java.io.IOException;


public interface EntryReader extends AutoCloseable{
    String next() throws IOException;
    
}
