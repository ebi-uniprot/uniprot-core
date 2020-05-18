package org.uniprot.core.xml.feature.antigen;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Sequence;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.antigen.impl.AntigenEntryBuilder;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.xml.jaxb.feature.EntryFeature;
import org.uniprot.core.xml.proteome.ProteomeConverter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 15/05/2020
 */
class AntigenEntryConverterTest {

    @Test
    void testConvertSimple() {
        AntigenEntryConverter converter = new AntigenEntryConverter();
        AntigenEntry antigenEntry = new AntigenEntryBuilder().build();
        EntryFeature xml = converter.toXml(antigenEntry);
        AntigenEntry converted = converter.fromXml(xml);
        assertEquals(antigenEntry, converted);
    }
    @Test
    void testConvertComplete() {
        AntigenEntryConverter converter = new AntigenEntryConverter();
        AntigenEntry antigenEntry = createAntigenEntry();
        EntryFeature xml = converter.toXml(antigenEntry);
        AntigenEntry converted = converter.fromXml(xml);
        assertEquals(antigenEntry, converted);
    }

    private AntigenEntry createAntigenEntry() {
        return new AntigenEntryBuilder()
                .primaryAccession("P21802")
                .uniProtkbId("P21802_HUMAN")
                .sequence(getSequence())
                .organism(getOrganism())
                .featuresAdd(FeatureConverterTest.getAntigenFeature())
                .sequence(new SequenceBuilder("AAAAAAAAAA").build())
                .build();
    }

    private Organism getOrganism() {
        return new OrganismBuilder()
                .taxonId(9606)
                .scientificName("Homo sapiens")
                .build();
    }

    private Sequence getSequence() {
        return new SequenceBuilder("AAAAAAAAAAA").build();
    }

}