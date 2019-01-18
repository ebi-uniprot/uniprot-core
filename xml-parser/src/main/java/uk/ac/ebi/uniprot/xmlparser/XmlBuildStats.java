package uk.ac.ebi.uniprot.xmlparser;


public interface XmlBuildStats {
   String getOuputFile();
   String getFailedEntryFile();
   long getNumberOfEntrySucceeded();
   long getNumberOfEntryFailed();
   String getReport();

}
