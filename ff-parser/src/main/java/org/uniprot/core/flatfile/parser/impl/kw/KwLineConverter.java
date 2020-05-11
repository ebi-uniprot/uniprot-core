package org.uniprot.core.flatfile.parser.impl.kw;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.exception.ParseKeywordException;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.KeywordBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KwLineConverter extends EvidenceCollector
        implements Converter<KwLineObject, List<Keyword>> {
    private final Map<String, Pair<String, KeywordCategory>> keywordMap;
    private final boolean ignoreWrongId;
    private static Pair<String, KeywordCategory> DEFAULT_KEYWORD_ID =
            new PairImpl<>("", KeywordCategory.UNKNOWN);

    public KwLineConverter(Map<String, Pair<String, KeywordCategory>> keywordMap) {
        this(keywordMap, true);
    }

    public KwLineConverter(
            Map<String, Pair<String, KeywordCategory>> keywordMap, boolean ignoreWrongId) {
        this.keywordMap = keywordMap;
        this.ignoreWrongId = ignoreWrongId;
    }

    @Override
    public List<Keyword> convert(KwLineObject f) {
        Map<Object, List<Evidence>> evidences =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        List<Keyword> keywords = new ArrayList<>();
        for (String kw : f.keywords) {
            Pair<String, KeywordCategory> keywordId =
                    keywordMap.getOrDefault(kw, DEFAULT_KEYWORD_ID);
            if (!ignoreWrongId && keywordId.equals(DEFAULT_KEYWORD_ID)) {
                throw new ParseKeywordException(kw + " does not match keyword entry.");
            }

            keywords.add(
                    new KeywordBuilder()
                            .id(keywordId.getKey())
                            .name(kw)
                            .category(keywordId.getValue())
                            .evidencesSet(evidences.get(kw))
                            .build());
        }
        return keywords;
    }
}
