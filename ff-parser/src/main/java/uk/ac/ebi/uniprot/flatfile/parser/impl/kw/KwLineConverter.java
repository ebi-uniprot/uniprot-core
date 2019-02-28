package uk.ac.ebi.uniprot.flatfile.parser.impl.kw;

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
    private final Map<String,String> keywordMap;
    private final boolean ignoreWrongId;

    public KwLineConverter(Map<String,String> keywordMap) {
        this(keywordMap, true);
    }

    public KwLineConverter(Map<String,String> keywordMap, boolean ignoreWrongId) {
        this.keywordMap = keywordMap;
        this.ignoreWrongId = ignoreWrongId;
    }


    @Override
    public List<Keyword> convert(KwLineObject f) {
        Map<Object, List<Evidence>> evidences = EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        List<Keyword> keywords = new ArrayList<>();
        for (String kw : f.keywords) {
            String keywordId= keywordMap.getOrDefault(kw,"");
            if (!ignoreWrongId && keywordId.isEmpty()) {
                throw new ParseKeywordException(kw + " does not match keyword entry.");
            }
            keywords.add(new KeywordBuilder(keywordId, kw, evidences.get(kw)).build());
        }
        return keywords;
    }

}
