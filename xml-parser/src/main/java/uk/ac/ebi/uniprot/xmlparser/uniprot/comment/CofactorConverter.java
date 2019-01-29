package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CofactorType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

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
        CofactorReferenceType type = CofactorReferenceType.typeOf(xmlObj.getDbReference().getType());
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceBuilder<CofactorReferenceType>()
                        .databaseType(type)
                        .id(xmlObj.getDbReference().getId())
                        .build();

        return new CofactorBuilder()
                .name(name)
                .reference(reference)
                .evidences(evidences)
                .build();
    }

    @Override
    public CofactorType toXml(Cofactor cofactor) {
        if (cofactor == null)
            return null;
        CofactorType xmlCofactor = xmlUniprotFactory.createCofactorType();
        xmlCofactor.setName(cofactor.getName());
        DbReferenceType dbref = xmlUniprotFactory.createDbReferenceType();
        dbref.setType(cofactor.getCofactorReference().getDatabaseType().toDisplayName());
        dbref.setId(cofactor.getCofactorReference().getId());
        xmlCofactor.setDbReference(dbref);
        List<Evidence> evidenceIds = cofactor.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty())
                xmlCofactor.getEvidence().addAll(evs);
        }
        return xmlCofactor;
    }

}
