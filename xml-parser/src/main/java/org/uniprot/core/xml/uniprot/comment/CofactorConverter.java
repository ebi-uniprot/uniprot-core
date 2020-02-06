package org.uniprot.core.xml.uniprot.comment;

import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CofactorType;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

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
        CofactorReferenceType type =
                CofactorReferenceType.typeOf(xmlObj.getDbReference().getType());
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceBuilder<CofactorReferenceType>()
                        .databaseType(type)
                        .id(xmlObj.getDbReference().getId())
                        .build();

        return new CofactorBuilder()
                .name(name)
                .reference(reference)
                .evidencesSet(evidences)
                .build();
    }

    @Override
    public CofactorType toXml(Cofactor cofactor) {
        if (cofactor == null) return null;
        CofactorType xmlCofactor = xmlUniprotFactory.createCofactorType();
        xmlCofactor.setName(cofactor.getName());
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(cofactor.getCofactorReference().getDatabaseType().toDisplayName());
        dbref.setId(cofactor.getCofactorReference().getId());
        xmlCofactor.setDbReference(dbref);
        List<Evidence> evidenceIds = cofactor.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty()) xmlCofactor.getEvidence().addAll(evs);
        }
        return xmlCofactor;
    }
}
