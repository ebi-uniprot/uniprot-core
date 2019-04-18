package uk.ac.ebi.uniprot.flatfile.parser.impl.kw;

import uk.ac.ebi.uniprot.common.Pair;
import uk.ac.ebi.uniprot.common.PairImpl;
import uk.ac.ebi.uniprot.cv.keyword.KeywordCategory;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.builder.KeywordBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.exception.ParseKeywordException;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceConverterHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KwLineConverter extends EvidenceCollector implements Converter<KwLineObject, List<Keyword>> {
    private final Map<String,Pair<String, KeywordCategory> > keywordMap;
    private final boolean ignoreWrongId;
    private static Pair<String, KeywordCategory> DEFAULT_KEYWORD_ID = new PairImpl<>("", KeywordCategory.UNKNOWN);

    public KwLineConverter(Map<String, Pair<String, KeywordCategory> > keywordMap) {
        this(keywordMap, true);
    }

    public KwLineConverter(Map<String, Pair<String, KeywordCategory>> keywordMap, boolean ignoreWrongId) {
        this.keywordMap = keywordMap;
        this.ignoreWrongId = ignoreWrongId;
    }


    @Override
    public List<Keyword> convert(KwLineObject f) {
        Map<Object, List<Evidence>> evidences = EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        List<Keyword> keywords = new ArrayList<>();
        for (String kw : f.keywords) {
            Pair<String, KeywordCategory> keywordId= keywordMap.getOrDefault(kw, DEFAULT_KEYWORD_ID );
            if (!ignoreWrongId && keywordId.equals(DEFAULT_KEYWORD_ID)) {
                throw new ParseKeywordException(kw + " does not match keyword entry.");
            }
            
            keywords.add(new KeywordBuilder(keywordId.getKey(), kw, keywordId.getValue(), evidences.get(kw)).build());
        }
        return keywords;
    }

}
