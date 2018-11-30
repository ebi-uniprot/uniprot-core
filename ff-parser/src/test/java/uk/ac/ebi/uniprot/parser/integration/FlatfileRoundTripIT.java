package uk.ac.ebi.uniprot.parser.integration;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.parser.UniProtParser;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniProtEntryIterator;
import uk.ac.ebi.uniprot.parser.impl.EntryBufferedReader;


public class FlatfileRoundTripIT {
	  private boolean isPublic = true;

	    public static void main(String[] args) {
	        if (args.length == 0) {
	            System.err.println("Please set arguments.");
	            System.exit(1);
	        }
	        FlatfileRoundTripIT test = new FlatfileRoundTripIT();
	        if (args.length == 2) {
	            test.setIsFileWithPublic(args[1].equals("T"));
	        }
	        test.testRead(args[0]);
	    }

	    private void testRead(String filename) {
	    	DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
	    	iterator.setInput(filename);
	        int count = 0;
	        while(iterator.hasNext()) {
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
	            System.out.println("Test Entry: " + entry.getPrimaryAccession().getValue());
	            String ff = null;

	            if (!isPublic)
	                ff = UniProtFlatfileWriter.write(entry, false, true);
	            else
	                ff = UniProtFlatfileWriter.write(entry, true, false);
	            System.out.println(ff);
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
	        List<UniProtDBCrossReference> emblCrossReferences = entry.getDatabaseCrossReferences().getCrossReferencesByType("EMBL");
	        for (UniProtDBCrossReference embl : emblCrossReferences) {
	            String db = embl.getDatabaseType().getName();
	            String emblPrimaryId = embl.getId();
	            String emblProteinId = embl.getProperties().get(0).getValue();
	            String param3 = embl.getProperties().get(1).getValue();
	            String param4 = embl.getProperties().get(2).getValue();

	            System.out.println(db + ": " + emblPrimaryId + "; " + emblProteinId + "; " + param3 + "; " + param4);
	        }
	        List<UniProtDBCrossReference> refseqCrossReference = entry.getDatabaseCrossReferences().getCrossReferencesByType("RefSeq");
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
	            if(xref.getProperties().size()>1)	        
	                param3 = xref.getProperties().get(1).getValue();
	            String param4 = null;
	            if(xref.getProperties().size()>1)
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
