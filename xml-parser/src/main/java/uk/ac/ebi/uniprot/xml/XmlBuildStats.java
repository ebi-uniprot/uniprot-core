package uk.ac.ebi.uniprot.xml;


public interface XmlBuildStats {
   String getOuputFile();
   String getFailedEntryFile();
   long getNumberOfEntrySucceeded();
   long getNumberOfEntryFailed();
   String getReport();

}
