package uk.ac.ebi.uniprot.flatfile.parser;

import uk.ac.ebi.uniprot.common.Pair;
import uk.ac.ebi.uniprot.cv.keyword.KeywordCategory;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;
import java.util.Map;

public interface SupportingDataMap {

    Map<String, Pair<String, KeywordCategory>> getKeywordMap();

    Map<String, String> getDiseaseMap();

    Map<String, Map<String, List<Evidence>>> getGoEvidencesMap();

    Map<String, String> getSubcellularLocationMap();

}
