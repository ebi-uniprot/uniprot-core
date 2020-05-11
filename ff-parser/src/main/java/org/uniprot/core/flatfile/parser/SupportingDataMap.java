package org.uniprot.core.flatfile.parser;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SupportingDataMap extends Serializable {

    Map<String, Pair<String, KeywordCategory>> getKeywordMap();

    Map<String, String> getDiseaseMap();

    Map<String, Map<String, List<Evidence>>> getGoEvidencesMap();

    Map<String, String> getSubcellularLocationMap();
}
