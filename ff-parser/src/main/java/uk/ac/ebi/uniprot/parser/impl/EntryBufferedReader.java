package uk.ac.ebi.uniprot.parser.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import uk.ac.ebi.uniprot.parser.EntryReader;

public class EntryBufferedReader implements EntryReader {
	private BufferedReader reader;
	/**
	 * Where to split entries
	 */
	public static final String ENTRY_END_TOKEN = "//";

	private boolean available = true;

	public EntryBufferedReader(String fileName) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(fileName);

		if (fileName.endsWith(".gz")) {
			is = new GZIPInputStream(is);
		}
		reader = new BufferedReader(new InputStreamReader(is));
	}

	public String next() throws IOException {
		StringBuffer new_file_entry = new StringBuffer();
		String new_line = null;
		if (available) {
			do {
				new_line = this.reader.readLine();

				new_file_entry.append(new_line);
				new_file_entry.append('\n');
			} while (new_line != null && !new_line.equals(ENTRY_END_TOKEN));
			if (new_line == null) {
				available = false;
			}
		}
		if (new_file_entry.length() <= 0 || !available) {
			return null;
		}
		return new_file_entry.toString();
		//
	}

	@Override
	public void close() throws IOException {
		this.reader.close();

	}

}
