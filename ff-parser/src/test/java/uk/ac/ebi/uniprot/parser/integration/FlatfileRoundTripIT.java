package uk.ac.ebi.uniprot.parser.integration;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jboss.logging.Logger;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.parser.UniProtEntryIterator;
import uk.ac.ebi.uniprot.parser.UniProtParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.ffwriter.FlatfileWriter;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniProtEntryIterator;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.EntryBufferedReader;
import uk.ac.ebi.uniprot.parser.impl.EntryBufferedReader2;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

public class FlatfileRoundTripIT {
	Logger log = Logger.getLogger(FlatfileRoundTripIT.class);
	private boolean isPublic = false;
	private boolean start = false;
	UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
	EntryObjectConverter entryObjectConverter = new EntryObjectConverter("", "", true);
	FlatfileWriter<UniProtEntry> ffWriter = new UniProtFlatfileWriter();

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Please set arguments.");
			System.exit(1);
		}
		FlatfileRoundTripIT test = new FlatfileRoundTripIT();
		// if (args.length == 2) {
		// test.setIsFileWithPublic(args[1].equals("T"));
		// }
		test.roundtrip(args[0]);
	}
	
	private void testIterator(String filename) throws Exception{
		UniProtEntryIterator iterator = new  DefaultUniProtEntryIterator();
		iterator.setInput(filename, "", "");
		LocalTime time = LocalTime.now();
		System.out.println("using EntryBufferedReader");
		System.out.println(time.toString());
		int count =0;
		while(iterator.hasNext()) {
			UniProtEntry entry = iterator.next();
			count++;
			if(count%10000 ==0) {
				System.out.println( LocalTime.now().toString() +"\t" + count);
			}
		}
		LocalTime end = LocalTime.now();
		System.out.println(end);
		Duration duration = Duration.between(time, end);
	
		System.out.println(duration.toString());
	
	}
	private void compareFileReader(String filename) throws Exception{
		EntryBufferedReader2 reader = new EntryBufferedReader2(filename);
		String entry = null;
		LocalTime time = LocalTime.now();
		System.out.println("using EntryBufferedReader");
		System.out.println(time.toString());
		int count =0;
		while ((entry = reader.next()) != null) {
			//EntryObject parse = entryParser.parse(entry);
		//	UniProtEntry converted = entryObjectConverter.convert(parse);
		//	assertNotNull(converted);
			count++;
			if(count%10000 ==0) {
				System.out.println( LocalTime.now().toString() +"\t" + count);
			}
		}
		reader.close();
		
	
		LocalTime end = LocalTime.now();
		System.out.println(end);
		Duration duration = Duration.between(time, end);
	
		System.out.println(duration.toString());
		
		EntryBufferedReader2 reader2 = new EntryBufferedReader2(filename);
		LocalTime start = LocalTime.now();
		System.out.println("using EntryBufferedReader2");
		System.out.println(start.toString());
		count =0;
		while ((entry = reader2.next()) != null) {
//			EntryObject parse = entryParser.parse(entry);
//			UniProtEntry converted = entryObjectConverter.convert(parse);
//			assertNotNull(converted);
			count++;
			if(count%10000 ==0) {
				System.out.println( LocalTime.now().toString() +"\t" + count);
			}
		}
		reader2.close();
		end = LocalTime.now();
		System.out.println(end);
		Duration duration2 = Duration.between(start, end);
		System.out.println(duration2.toString());
	}

	private void roundtrip(String filename) throws IOException {
		EntryBufferedReader reader = new EntryBufferedReader(filename);
		String entry = null;
		int failedCount = 0;
		int totalCount = 0;
		while ((entry = reader.next()) != null) {
			if (entry.length() > 0) {
				if (!testEntry(entry)) {
					failedCount++;
				}
				totalCount++;

			}
			if (totalCount % 5000 == 0) {
				System.out.println("parsed entries:" + totalCount + " failed: " + failedCount);
			}
		}
		System.out.println("total parsed entries:" + totalCount + " failed: " + failedCount);
		reader.close();

	}
	private boolean testEntry(String entryStr) {
		EntryObject parse = entryParser.parse(entryStr);
		UniProtEntry converted = entryObjectConverter.convert(parse);
		assertNotNull(converted);
		String convertedEntryStr = ffWriter.write(converted, isPublic);
		EntryObject parse2 =null;
		
		try {

			 parse2 = entryParser.parse(convertedEntryStr);
		}catch(Exception e) {
			System.out.println(entryStr);
			System.out.println(convertedEntryStr);
			throw e;
		}

		UniProtEntry converted2 = entryObjectConverter.convert(parse2);
		boolean b = converted2.equals(converted);
		if (!b) {
			System.out.println(entryStr);
			
			
			System.out.println( converted.getPrimaryAccession().getValue());
		}
		return b;
	}

	private void testRead(String filename) {
		DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
		iterator.setInput(filename, "", "");
		int count = 0;
		while (iterator.hasNext()) {
			UniProtEntry entry = iterator.next();
			System.out.println(entry.getPrimaryAccession().getValue());
			if (++count % 100 == 0)
				System.out.println("Number of entries parsed: " + count);
		}
		
	}

	public void setIsFileWithPublic(boolean hasEvidence) {
		this.isPublic = hasEvidence;
	}

	public void parseAndTest(String file) throws IOException {
		EntryBufferedReader reader = new EntryBufferedReader(file);
		start = false;
		String entry = null;
		String accession = "";
		String failed = "parserFailed";
		BufferedWriter writer2 = new BufferedWriter(new FileWriter("failedEntries.dat"));
		BufferedWriter writer3 = new BufferedWriter(new FileWriter("failedParsingEntries.dat"));
		BufferedWriter writer = new BufferedWriter(new FileWriter(failed));
		Date d = new Date();
		int count = 0;

		while ((entry = reader.next()) != null) {
			if (entry.length() > 0)
				accession = testEntry(entry, accession, writer, writer2, writer3);
			count++;
			if (count % 1000 == 0) {
				System.out.println("parsed entries:" + count);
			}
		}
		Date d2 = new Date();
		System.out.println("Time =" + (d2.getTime() - d.getTime()));
		System.out.println("Count =" + count);
		writer2.flush();
		writer3.flush();
		writer.close();
		writer2.close();
		writer3.close();
		reader.close();
	}

	public String testEntry(String entryText, String prevEntry, Writer writer, Writer writer2, Writer writer3) {
		try {
			UniProtEntry entry = UniProtParser.parse(entryText, true);
			assertNotNull(entry);
			String currentAcc = entry.getPrimaryAccession().getValue();
			if (currentAcc.equals("A8G1C8"))
				start = true;
			if (!start)
				return currentAcc;
			System.out.println("Test Entry: " + entry.getPrimaryAccession().getValue());
			String ff = null;

			if (!isPublic)
				ff = UniProtFlatfileWriter.write(entry, false, true);
			else
				ff = UniProtFlatfileWriter.write(entry, true, false);
			// System.out.println(ff);
			String value = compareFF(entryText, ff);
			if (value.length() > 0) {
				writer.write("Test Entry: " + entry.getPrimaryAccession().getValue() + "\n");
				writer.write(value);
				writer.flush();

				writer2.write(entryText);
				writer2.flush();
			}
			return entry.getPrimaryAccession().getValue();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writer.write("Previous entry:" + prevEntry + "\n");
				writer.write(e.getMessage() + "\n");

				writer3.write(entryText);
				writer3.flush();

			} catch (Exception ee) {
				e.printStackTrace();
			}

		}
		return prevEntry;
	}

	private String compareFF(String expected, String returned) {
		String[] expected2 = expected.split("\n");
		String[] returned2 = returned.split("\n");
		if (expected2.length != returned2.length) {
			System.out.println("number of line is different: " + expected2.length + "\t" + returned2.length);
		}
		int j = 0;
		String value = "";
		Set<String> drReturned = new TreeSet<>();
		Set<String> drExpected = new TreeSet<>();
		int returnLength = returned2.length;
		for (int i = 0; i < expected2.length; i++) {
			if (expected2[i].startsWith("DR   ")) {
				drReturned.add(returned2[j]);
				drExpected.add(expected2[i]);

			} else if (j >= returnLength) {
				System.err.println(expected2[i]);
			} else if (!expected2[i].equals(returned2[j])) {
				boolean found = false;
				int found1 = find(returned2, expected2[i], j);
				if (found1 != -1) {
					found = true;
					j = found1;
				}
				if (!found) {
					value += "Expected:" + expected2[i] + "\n";
					value += "Parsed==:" + returned2[j] + "\n";

					System.err.println("Expected:" + expected2[i]);
					System.err.println("Parsed==:" + returned2[j]);
				}

				if (j >= returned2.length)
					break;

			}
			j++;
		}

		drReturned.removeAll(drExpected);
		if (drReturned.size() != 0) {
			System.err.println(drReturned.toString());
		}
		return value;

	}

	private int find(String[] returned2, String value, int j) {
		int start = j - 6;
		if (start < 0)
			start = 0;
		int end = j + 6;
		if (end >= returned2.length) {
			end = returned2.length - 1;
		}
		for (int i = 0; i < returned2.length; i++) {
			if (value.equals(returned2[i]))
				return i;
		}
		return -1;
	}

	void accessCrossReference(UniProtEntry entry) {
		List<UniProtDBCrossReference> emblCrossReferences = entry.getDatabaseCrossReferences()
				.getCrossReferencesByType("EMBL");
		for (UniProtDBCrossReference embl : emblCrossReferences) {
			String db = embl.getDatabaseType().getName();
			String emblPrimaryId = embl.getId();
			String emblProteinId = embl.getProperties().get(0).getValue();
			String param3 = embl.getProperties().get(1).getValue();
			String param4 = embl.getProperties().get(2).getValue();

			System.out.println(db + ": " + emblPrimaryId + "; " + emblProteinId + "; " + param3 + "; " + param4);
		}
		List<UniProtDBCrossReference> refseqCrossReference = entry.getDatabaseCrossReferences()
				.getCrossReferencesByType("RefSeq");
		for (UniProtDBCrossReference rs : refseqCrossReference) {
			String db = rs.getDatabaseType().getName();
			String rsId = rs.getId();
			String descriptoin = rs.getProperties().get(0).getValue();
			System.out.println(db + ": " + rsId + "; " + descriptoin);
		}
		Collection<UniProtDBCrossReference> allReferences = entry.getDatabaseCrossReferences().getCrossReferences();
		for (UniProtDBCrossReference xref : allReferences) {
			String dbName = xref.getDatabaseType().getName();
			String primaryId = xref.getId();
			String description = xref.getProperties().get(0).getValue();
			String param3 = null;
			if (xref.getProperties().size() > 1)
				param3 = xref.getProperties().get(1).getValue();
			String param4 = null;
			if (xref.getProperties().size() > 1)
				param4 = xref.getProperties().get(2).getValue();

			StringBuilder sb = new StringBuilder();
			sb.append(dbName).append(": ");
			sb.append(primaryId).append("; ");
			sb.append(description);
			if (param3 != null) {
				sb.append("; ").append(param3);
			}
			if (param4 != null) {
				sb.append("; ").append(param4);
			}
			sb.append(".");
			System.out.println(sb.toString());

		}

	}
}
