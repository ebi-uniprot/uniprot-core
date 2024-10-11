package org.uniprot.core.xml.uniparc;

import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.Entry;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;

public class UniParcEntryLightConverter implements Converter<Entry, UniParcEntryLight> {
    private static final String UNIPARC = "uniparc";
    private final ObjectFactory xmlFactory;
    private final SequenceFeatureConverter seqFeatureConverter;
    private final SequenceConverter sequenceConverter;

    public UniParcEntryLightConverter(){
        this(new ObjectFactory());
    }

    public UniParcEntryLightConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.seqFeatureConverter = new SequenceFeatureConverter(xmlFactory);
        this.sequenceConverter = new SequenceConverter(xmlFactory);
    }

    @Override
    public UniParcEntryLight fromXml(Entry xmlObj) {
        UniParcEntryLightBuilder builder = new UniParcEntryLightBuilder();
        builder.uniParcId(xmlObj.getAccession())
                .sequence(sequenceConverter.fromXml(xmlObj.getSequence()))
                .sequenceFeaturesSet(
                        xmlObj.getSignatureSequenceMatch().stream()
                                .map(seqFeatureConverter::fromXml)
                                .toList());
        return builder.build();
    }

    @Override
    public Entry toXml(UniParcEntryLight uniObj) {
        Entry entry = xmlFactory.createEntry();
        entry.setDataset(UNIPARC);
        entry.setAccession(uniObj.getUniParcId());
        entry.setSequence(sequenceConverter.toXml(uniObj.getSequence()));
        uniObj.getSequenceFeatures().stream()
                .map(seqFeatureConverter::toXml)
                .forEach(val -> entry.getSignatureSequenceMatch().add(val));
        return entry;
    }
}
