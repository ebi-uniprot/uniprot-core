package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.comment.impl.CofactorBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CofactorType;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.List;

public class CofactorConverter implements Converter<CofactorType, Cofactor> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;

    public CofactorConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public CofactorConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Cofactor fromXml(CofactorType xmlObj) {
        String name = xmlObj.getName();
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        CofactorDatabase type = CofactorDatabase.typeOf(xmlObj.getDbReference().getType());
        CrossReference<CofactorDatabase> reference =
                new CrossReferenceBuilder<CofactorDatabase>()
                        .database(type)
                        .id(xmlObj.getDbReference().getId())
                        .build();

        return new CofactorBuilder()
                .name(name)
                .cofactorCrossReference(reference)
                .evidencesSet(evidences)
                .build();
    }

    @Override
    public CofactorType toXml(Cofactor cofactor) {
        if (cofactor == null) return null;
        CofactorType xmlCofactor = xmlUniprotFactory.createCofactorType();
        xmlCofactor.setName(cofactor.getName());
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(cofactor.getCofactorCrossReference().getDatabase().getDisplayName());
        dbref.setId(cofactor.getCofactorCrossReference().getId());
        xmlCofactor.setDbReference(dbref);
        List<Evidence> evidenceIds = cofactor.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty()) xmlCofactor.getEvidence().addAll(evs);
        }
        return xmlCofactor;
    }
}
