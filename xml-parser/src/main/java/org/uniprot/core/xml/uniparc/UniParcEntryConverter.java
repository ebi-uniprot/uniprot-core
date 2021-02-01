package org.uniprot.core.xml.uniparc;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.Entry;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import com.google.common.base.Strings;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcEntryConverter implements Converter<Entry, UniParcEntry> {
    private static final String UNIPARC = "uniparc";
    private final ObjectFactory xmlFactory;
    private final SequenceFeatureConverter seqFeatureConverter;
    private final UniParcDBCrossReferenceConverter xrefConverter;
    private final SequenceConverter sequenceConverter;

    public UniParcEntryConverter() {
        this(new ObjectFactory(), null);
    }

    public UniParcEntryConverter(ObjectFactory xmlFactory, TaxonomyRepo taxonomyRepo) {
        this.xmlFactory = xmlFactory;
        this.seqFeatureConverter = new SequenceFeatureConverter(xmlFactory);
        this.xrefConverter = new UniParcDBCrossReferenceConverter(xmlFactory, taxonomyRepo);
        this.sequenceConverter = new SequenceConverter(xmlFactory);
    }

    public UniParcEntryConverter(TaxonomyRepo taxonomyRepo) {
        this(new ObjectFactory(), taxonomyRepo);
    }

    @Override
    public UniParcEntry fromXml(Entry xmlObj) {
        UniParcEntryBuilder builder = new UniParcEntryBuilder();
        builder.uniParcId(new UniParcIdBuilder(xmlObj.getAccession()).build())
                .sequence(sequenceConverter.fromXml(xmlObj.getSequence()))
                .sequenceFeaturesSet(
                        xmlObj.getSignatureSequenceMatch().stream()
                                .map(seqFeatureConverter::fromXml)
                                .collect(Collectors.toList()));
        if (xmlObj.getUniProtKBExclusion() != null) {
            builder.uniprotExclusionReason(xmlObj.getUniProtKBExclusion());
        }

        List<UniParcCrossReference> xrefs =
                xmlObj.getDbReference().stream()
                        .map(xrefConverter::fromXml)
                        .collect(Collectors.toList());
        builder.uniParcCrossReferencesSet(xrefs);

        return builder.build();
    }

    @Override
    public Entry toXml(UniParcEntry uniObj) {
        Entry entry = xmlFactory.createEntry();
        entry.setDataset(UNIPARC);
        entry.setAccession(uniObj.getUniParcId().getValue());
        if (!Strings.isNullOrEmpty(uniObj.getUniProtExclusionReason())) {
            entry.setUniProtKBExclusion(uniObj.getUniProtExclusionReason());
        }
        entry.setSequence(sequenceConverter.toXml(uniObj.getSequence()));
        uniObj.getUniParcCrossReferences().stream()
                .map(xrefConverter::toXml)
                .forEach(val -> entry.getDbReference().add(val));
        uniObj.getSequenceFeatures().stream()
                .map(seqFeatureConverter::toXml)
                .forEach(val -> entry.getSignatureSequenceMatch().add(val));
        return entry;
    }
}
