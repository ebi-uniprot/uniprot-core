package org.uniprot.core.parser.fasta;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcFastaParserTest {

    @Test
    void testToFasta() {
        UniParcEntry entry = create();
        String fasta = UniParcFastaParser.toFasta(entry);
        System.out.println(fasta);
        String expected =
                ">UPI0000083A08 status=active\n"
                        + "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n"
                        + "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    private UniParcEntry create() {
        String seq =
                "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT" + "LLRAIDWFRDNGYFNA";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcCrossReference> xrefs = getXrefs();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .build();
        return entry;
    }

    private List<SequenceFeature> getSeqFeatures() {
        List<Location> locations = Arrays.asList(new Location(12, 23), new Location(45, 89));
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .build();
        SequenceFeature sf3 =
                SequenceFeatureBuilder.from(sf).signatureDbType(SignatureDbType.PROSITE).build();
        return Arrays.asList(sf, sf3);
    }

    private List<UniParcCrossReference> getXrefs() {
        Organism taxonomy =
                new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "pvalue"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .propertiesSet(properties)
                        .taxonomy(taxonomy)
                        .proteinName("some pname")
                        .geneName("some gname")
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties.add(new Property("prop2", "pvalue2"));
        Organism taxonomy2 = new OrganismBuilder().taxonId(10090).scientificName("MOUSE").build();

        UniParcCrossReference xref2 =
                new UniParcCrossReferenceBuilder()
                        .versionI(1)
                        .database(UniParcDatabase.TREMBL)
                        .id("P52346")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 2, 12))
                        .lastUpdated(LocalDate.of(2017, 4, 23))
                        .propertiesSet(properties2)
                        .taxonomy(taxonomy2)
                        .proteinName("some pname")
                        .proteomeId("UP00000564")
                        .component("chromosome 1")
                        .build();

        return Arrays.asList(xref, xref2);
    }
}
