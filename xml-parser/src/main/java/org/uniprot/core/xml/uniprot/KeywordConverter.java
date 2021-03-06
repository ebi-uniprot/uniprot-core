package org.uniprot.core.xml.uniprot;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.KeywordBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.KeywordType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class KeywordConverter implements Converter<KeywordType, Keyword> {
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;

    public KeywordConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public KeywordConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Keyword fromXml(KeywordType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        String keywordValue = xmlObj.getValue();
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        return new KeywordBuilder()
                .id(xmlObj.getId())
                .name(keywordValue)
                .category(KeywordCategory.UNKNOWN)
                .evidencesSet(evidences)
                .build();
    }

    @Override
    public KeywordType toXml(Keyword uniObj) {
        if (Objects.isNull(uniObj)) return null;
        KeywordType xmlKeyword = xmlUniprotFactory.createKeywordType();
        String value = uniObj.getName();
        xmlKeyword.setValue(value);
        xmlKeyword.setId(uniObj.getId());

        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!ev.isEmpty()) xmlKeyword.getEvidence().addAll(ev);
        }
        return xmlKeyword;
    }
}
