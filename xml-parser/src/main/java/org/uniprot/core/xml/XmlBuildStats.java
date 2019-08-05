package org.uniprot.core.xml;


public interface XmlBuildStats {
   String getOuputFile();
   String getFailedEntryFile();
   long getNumberOfEntrySucceeded();
   long getNumberOfEntryFailed();
   String getReport();

}
