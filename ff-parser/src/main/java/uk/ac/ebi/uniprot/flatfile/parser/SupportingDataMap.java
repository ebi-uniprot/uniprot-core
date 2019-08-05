package uk.ac.ebi.uniprot.flatfile.parser;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.uniprot.core.common.Pair;
import org.uniprot.core.cv.keyword.KeywordCategory;

public interface SupportingDataMap extends Serializable {

    Map<String, Pair<String, KeywordCategory>> getKeywordMap();

    Map<String, String> getDiseaseMap();

    Map<String, Map<String, List<Evidence>>> getGoEvidencesMap();

    Map<String, String> getSubcellularLocationMap();

}
