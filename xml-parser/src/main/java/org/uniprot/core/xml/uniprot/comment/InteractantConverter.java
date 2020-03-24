package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.InteractantType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

/**
 * @author jluo
 * @date: 19 Mar 2020
 */
public class InteractantConverter implements Converter<InteractantType, Interactant> {
    private static final String UNIPROTKB = "UniProtKB";
    private final ObjectFactory xmlUniprotFactory;

    public InteractantConverter() {
        this(new ObjectFactory());
    }

    public InteractantConverter(ObjectFactory xmlUniprotFactory) {

        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Interactant fromXml(InteractantType interactantType) {
        InteractantBuilder builder = new InteractantBuilder();
        builder.intActId(interactantType.getIntactId());
        if (Utils.notNullNotEmpty(interactantType.getLabel())) {
            builder.geneName(interactantType.getLabel());
        }
        if (interactantType.getDbReference() != null) {
            builder.uniProtKBAccession(interactantType.getDbReference().getId())
                    .chainId(interactantType.getId());
        } else {
            UniProtKBAccession accession =
                    new UniProtKBAccessionBuilder(interactantType.getId()).build();
            if (accession.isValidAccession()) {
                builder.uniProtKBAccession(accession);
            } else builder.chainId(interactantType.getId());
        }

        return builder.build();
    }

    @Override
    public InteractantType toXml(Interactant interactant) {
        InteractantType interactantType = xmlUniprotFactory.createInteractantType();
        interactantType.setIntactId(interactant.getIntActId());
        if (Utils.notNullNotEmpty(interactant.getGeneName())) {
            interactantType.setLabel(interactant.getGeneName());
        }
        if (Utils.notNullNotEmpty(interactant.getChainId())) {
            interactantType.setId(interactant.getChainId());
            if ((interactant.getUniProtKBAccession() != null)
                    && Utils.notNullNotEmpty(interactant.getUniProtKBAccession().getValue())) {
                DbReferenceType xmlReference = xmlUniprotFactory.createDbReferenceType();
                xmlReference.setType(UNIPROTKB);
                xmlReference.setId(interactant.getUniProtKBAccession().getValue());
                interactantType.setDbReference(xmlReference);
            }
        } else {
            interactantType.setId(interactant.getUniProtKBAccession().getValue());
        }
        return interactantType;
    }
}
