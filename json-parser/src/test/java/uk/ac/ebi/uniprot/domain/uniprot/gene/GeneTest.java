package uk.ac.ebi.uniprot.domain.uniprot.gene;

import org.junit.Test;
import uk.ac.ebi.uniprot.ValidateJson;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneTest {

    @Test
    public void testGeneSimpleName() {
        Gene gene = createSimpleGene();
        ValidateJson.verifyJsonParser(gene);

        String expectedJson = "{\"geneName\":{\"value\":\"someGene\"}}";
        ValidateJson.verifySimpleJsonParser(gene,expectedJson);
    }

    @Test
    public void testFullGene() {
        Gene gene = createCompleteGene();
        ValidateJson.verifyJsonParser(gene);

        String expectedJson = "{\"geneName\":{\"value\":\"some Gene\"," +
                "\"evidences\":[{\"evidenceCode\":\"ECO_0000256\",\"source\":{\"databaseType\":{\"name\":\"PIRNR\"},\"id\":\"PIRNR001360\"}}]}," +

                "\"synonyms\":[{\"value\":\"some Syn\"," +
                "\"evidences\":[{\"evidenceCode\":\"ECO_0000256\",\"source\":{\"databaseType\":{\"name\":\"PIRNR\"},\"id\":\"PIRNR001361\"}}," +
                               "{\"evidenceCode\":\"ECO_0000269\",\"source\":{\"databaseType\":{\"name\":\"PubMed\"},\"id\":\"11389731\"}}]}]," +

                "\"orderedLocusNames\":[{\"value\":\"some locus\"," +
                "\"evidences\":[{\"evidenceCode\":\"ECO_0000256\",\"source\":{\"databaseType\":{\"name\":\"PIRNR\"},\"id\":\"PIRNR001362\"}}]}]," +

                "\"orfNames\":[{\"value\":\"some orf\"," +
                "\"evidences\":[{\"evidenceCode\":\"ECO_0000256\",\"source\":{\"databaseType\":{\"name\":\"PIRNR\"},\"id\":\"PIRNR001363\"}}," +
                               "{\"evidenceCode\":\"ECO_0000269\",\"source\":{\"databaseType\":{\"name\":\"PubMed\"},\"id\":\"11389730\"}}]}]}";

        ValidateJson.verifySimpleJsonParser(gene,expectedJson);
    }


    private Gene createCompleteGene(){
        List<Evidence> geneNameEvidences = Collections.singletonList(createEvidence("ECO:0000256|PIRNR:PIRNR001360"));
        GeneName geneName = GeneFactory.INSTANCE.createGeneName("some Gene", geneNameEvidences);

        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<Evidence> synEvidences = Arrays.asList(createEvidence("ECO:0000256|PIRNR:PIRNR001361"),
                createEvidence("ECO:0000269|PubMed:11389731"));
        GeneNameSynonym synonym = GeneFactory.INSTANCE.createGeneNameSynonym("some Syn", synEvidences);
        synonyms.add(synonym);

        List<OrderedLocusName> olnNames = new ArrayList<>();
        List<Evidence> olnNameEvidences = Collections.singletonList(createEvidence("ECO:0000256|PIRNR:PIRNR001362"));
        OrderedLocusName olnName = GeneFactory.INSTANCE.createOrderedLocusName("some locus", olnNameEvidences);
        olnNames.add(olnName);

        List<ORFName> orfNames = new ArrayList<>();
        List<Evidence> evidences = Arrays.asList(createEvidence("ECO:0000256|PIRNR:PIRNR001363"),
                createEvidence("ECO:0000269|PubMed:11389730"));
        orfNames.add(GeneFactory.INSTANCE.createORFName("some orf", evidences));

        return GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);
    }

    private Gene createSimpleGene() {
        GeneName geneName = GeneFactory.INSTANCE.createGeneName("someGene", Collections.emptyList());
        return GeneFactory.INSTANCE.createGene(geneName, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    private Evidence createEvidence(String evidenceStr) {
        return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
    }

}
