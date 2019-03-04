package uk.ac.ebi.uniprot.flatfile.parser;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;
import java.util.Map;

public interface SupportingDataMap {

    Map<String, String> getKeywordMap();

    Map<String, String> getDiseaseMap();

    Map<String, Map<String, List<Evidence>>> getGoEvidencesMap();

    Map<String, String> getSubcellularLocationMap();

}
