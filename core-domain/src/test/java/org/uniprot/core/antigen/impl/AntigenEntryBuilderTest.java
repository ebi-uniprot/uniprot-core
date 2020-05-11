package org.uniprot.core.antigen.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
class AntigenEntryBuilderTest {

    @Test
    void canSetPrimaryAccessionString() {
        String primaryAccession = "P21802";
        AntigenEntry entry = new AntigenEntryBuilder().primaryAccession(primaryAccession).build();
        assertNotNull(entry.getPrimaryAccession());
        assertEquals(primaryAccession, entry.getPrimaryAccession().getValue());
    }

    @Test
    void canSetPrimaryAccession() {
        UniProtKBAccession primaryAccession = new UniProtKBAccessionBuilder("P21802").build();
        AntigenEntry entry = new AntigenEntryBuilder().primaryAccession(primaryAccession).build();
        assertEquals(primaryAccession, entry.getPrimaryAccession());
    }

    @Test
    void canSetUniProtkbIdString() {
        String uniProtkbId = "ID VALUE";
        AntigenEntry entry = new AntigenEntryBuilder().uniProtkbId(uniProtkbId).build();
        assertNotNull(entry.getUniProtkbId());
        assertEquals(uniProtkbId, entry.getUniProtkbId().getValue());
    }

    @Test
    void canSetUniProtkbId() {
        UniProtKBId uniProtkbId = new UniProtKBIdBuilder("ID VALUE").build();
        AntigenEntry entry = new AntigenEntryBuilder().uniProtkbId(uniProtkbId).build();
        assertEquals(uniProtkbId, entry.getUniProtkbId());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().taxonId(10L).build();
        AntigenEntry entry = new AntigenEntryBuilder().organism(organism).build();
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSetSequence() {
        Sequence sequence = new SequenceBuilder("SEQUENCE VALUE").build();
        AntigenEntry entry = new AntigenEntryBuilder().sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canAddFeatures() {
        AntigenFeature feature =
                new AntigenFeatureBuilder().description("description value").build();
        AntigenEntry entry = new AntigenEntryBuilder().featuresAdd(feature).build();
        assertNotNull(entry.getFeatures());
        assertTrue(entry.getFeatures().contains(feature));
    }

    @Test
    void canSetFeatures() {
        AntigenFeature feature =
                new AntigenFeatureBuilder().description("description value").build();
        List<AntigenFeature> features = Collections.singletonList(feature);
        AntigenEntry entry = new AntigenEntryBuilder().featuresSet(features).build();
        assertEquals(features, entry.getFeatures());
    }

    @Test
    void canCreateBuilderFromInstance() {
        AntigenEntry obj = new AntigenEntryBuilder().build();
        AntigenEntryBuilder builder = AntigenEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        AntigenEntry obj = new AntigenEntryBuilder().build();
        AntigenEntry obj2 = new AntigenEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
