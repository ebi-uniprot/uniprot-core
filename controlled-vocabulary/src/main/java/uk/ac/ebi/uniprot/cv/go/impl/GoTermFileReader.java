package uk.ac.ebi.uniprot.cv.go.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.ac.ebi.uniprot.cv.go.GoTerm;
import uk.ac.ebi.uniprot.cv.go.GoTermReader;
import uk.ac.ebi.uniprot.cv.impl.AbstractFileReader;

public class GoTermFileReader implements GoTermReader {
	private static final String COMMENT_PREFIX = "!";
	private static final String SEPARATOR = "\t";
	private static final String NOT_OBSOLETE= "N";
	private static final String FILENAME ="GO.terms";
	private final String filepath;
	public GoTermFileReader(String filepath) {
		this.filepath = filepath;
	}

	
	public Map<String, GoTerm> read() {
		String filename = filepath ;
		if(!filename.endsWith(FILENAME)){
			filename +=File.separator + FILENAME;
		}		
	
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {	
			return stream.map(this::readLine)
			.filter(val->val !=null)
			.collect(Collectors.toMap(GoTerm::getId, val->val ));
		} catch (IOException e) {
			throw new RuntimeException (e);
		}
	}
	private GoTerm readLine(String line) {
		if(line.startsWith(COMMENT_PREFIX))
			return null;
		
		String[] tokens = line.split(SEPARATOR);
		if(tokens.length==4)  {		
			if(tokens[1].equals(NOT_OBSOLETE)) {
				return  new GoTermImpl(tokens[0], tokens[2]);
			}
		}
		return null;
	}
	
}
