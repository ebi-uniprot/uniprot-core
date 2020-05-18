package org.uniprot.core.xml.feature;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.feature.EvidenceType;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;

/**
 * @author lgonzales
 * @since 15/05/2020
 */
public class FeatureEvidenceConverter  implements Converter<EvidenceType, Evidence> {

    private final ObjectFactory objectFactory;
    private final EvidenceCrossRefConverter evidenceCrossRefConverter;

    public FeatureEvidenceConverter() {
        this(new ObjectFactory());
    }
    public FeatureEvidenceConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.evidenceCrossRefConverter = new EvidenceCrossRefConverter(objectFactory);
    }

    @Override
    public Evidence fromXml(EvidenceType xmlObj) {
        EvidenceBuilder builder = new EvidenceBuilder();
        if(Utils.notNull(xmlObj.getCode())){
            builder.evidenceCode(EvidenceCode.typeOf(xmlObj.getCode()));
        }
        if(Utils.notNull(xmlObj.getDbReference())){
            CrossReference<EvidenceDatabase> crossReference = evidenceCrossRefConverter.fromXml(xmlObj.getDbReference());
            if(crossReference.hasId()) {
                builder.databaseId(crossReference.getId());
            }
            if(crossReference.hasDatabase()) {
                builder.databaseName(crossReference.getDatabase().getName());
            }
        }
        return builder.build();
    }

    @Override
    public EvidenceType toXml(Evidence uniObj) {
        EvidenceType evidenceType = objectFactory.createEvidenceType();
        if(Utils.notNull(uniObj.getEvidenceCode())){
            evidenceType.setCode(uniObj.getEvidenceCode().getDisplayName());
        }
        if(Utils.notNull(uniObj.getEvidenceCrossReference())){
            evidenceType.setDbReference(evidenceCrossRefConverter.toXml(uniObj.getEvidenceCrossReference()));
        }
        return evidenceType;
    }

}
