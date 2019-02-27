package uk.ebi.uniprot.scorer.uniprotkb;

import java.util.List;

public interface ScoreConfigure {
    String getInputFile();
    String getOutputFile();
    List<String> getEvidences();
    boolean validate();
    String getUsage();
    String getKeywordFile();
    String getDiseaseFile();
    String getGOFile();
    String getSubcellLocationFile();
}
