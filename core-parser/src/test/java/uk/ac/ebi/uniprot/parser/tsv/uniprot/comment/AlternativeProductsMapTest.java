package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.AlternativeProductsComment;
import org.uniprot.core.uniprot.comment.CommentType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AlternativeProductsMapTest {


    @Test
    void testAlternativeProductsMapping() {
        String alternativeProductsLine = "CC   -!- ALTERNATIVE PRODUCTS:\n" +
                "CC       Event=Alternative promoter usage, Alternative initiation; Named isoforms=3;\n" +
                "CC       Name=Genome polyprotein;\n" +
                "CC         IsoId=Q672I1-1; Sequence=Displayed;\n" +
                "CC         Note=Produced from the genomic RNA.;\n" +
                "CC       Name=Subgenomic capsid protein; Synonyms=VP1;\n" +
                "CC         IsoId=Q672I1-2; Sequence=VSP_034391;\n" +
                "CC         Note=Produced from the subgenomic RNA by alternative promoter\n" +
                "CC         usage.;\n" +
                "CC       Name=Uncharacterized protein VP3;\n" +
                "CC         IsoId=Q672I0-1; Sequence=External;\n" +
                "CC         Note=Produced by alternative initiation from the subgenomic\n" +
                "CC         RNA.;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(alternativeProductsLine);

        List<AlternativeProductsComment> alternativeProductsComments = entry.getCommentByType(CommentType.ALTERNATIVE_PRODUCTS);
        assertNotNull(entry);
        AlternativeProductsMap alternativeProductsMap = new AlternativeProductsMap(alternativeProductsComments);
        Map<String,String> mappedAlternativeProducts = alternativeProductsMap.attributeValues();
        assertNotNull(mappedAlternativeProducts);
        String value = mappedAlternativeProducts.get("cc:alternative_products");
        String expectedValue = "ALTERNATIVE PRODUCTS:  Event=Alternative promoter usage, Alternative initiation; " +
                "Named isoforms=3; Name=Genome polyprotein; IsoId=Q672I1-1; Sequence=Displayed; " +
                "Note=Produced from the genomic RNA.; Name=Subgenomic capsid protein; Synonyms=VP1; " +
                "IsoId=Q672I1-2; Sequence=VSP_034391; Note=Produced from the subgenomic RNA by alternative " +
                "promoter usage.; Name=Uncharacterized protein VP3; IsoId=Q672I0-1; Sequence=External; " +
                "Note=Produced by alternative initiation from the subgenomic RNA.;";
        assertEquals(expectedValue,value);
    }

    @Test
    void testAlternativeSplicingMapping() {
        String alternativeProductsLine = "CC   -!- ALTERNATIVE PRODUCTS:\n" +
                "CC       Event=Alternative splicing; Named isoforms=2;\n" +
                "CC       Name=2;\n" +
                "CC         IsoId=P37238-1; Sequence=Displayed;\n" +
                "CC       Name=1;\n" +
                "CC         IsoId=P37238-2; Sequence=VSP_003647;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(alternativeProductsLine);

        List<AlternativeProductsComment> alternativeProductsComments = entry.getCommentByType(CommentType.ALTERNATIVE_PRODUCTS);
        assertNotNull(entry);
        AlternativeProductsMap alternativeProductsMap = new AlternativeProductsMap(alternativeProductsComments);
        Map<String,String> mappedAlternativeProducts = alternativeProductsMap.attributeValues();
        assertNotNull(mappedAlternativeProducts);
        String value = mappedAlternativeProducts.get("cc:alternative_products");
        String expectedValue = "ALTERNATIVE PRODUCTS:  Event=Alternative splicing; Named isoforms=2; " +
                "Name=2; IsoId=P37238-1; Sequence=Displayed; " +
                "Name=1; IsoId=P37238-2; Sequence=VSP_003647;";
        assertEquals(expectedValue,value);
    }

    @Test
    void testAlternativeProductsWithEvidencesMapping() {
        String alternativeProductsLine = "CC   -!- ALTERNATIVE PRODUCTS:\n" +
                "CC       Event=Ribosomal frameshifting; Named isoforms=3;\n" +
                "CC         Comment=This strategy of translation probably allows the virus\n" +
                "CC         to modulate the quantity of each viral protein. {ECO:0000305};\n" +
                "CC       Name=Gag-Pro-Pol polyprotein;\n" +
                "CC         IsoId=P03362-1; Sequence=Displayed;\n" +
                "CC         Note=Produced by -1 ribosomal frameshiftings at the gag-pro and\n" +
                "CC         gag-pol genes boundaries. {ECO:0000269|PubMed:8416368};\n" +
                "CC       Name=Gag-Pro polyprotein;\n" +
                "CC         IsoId=P10274-1; Sequence=External;\n" +
                "CC         Note=Produced by -1 ribosomal frameshifting at the gag-pro genes\n" +
                "CC         boundary. {ECO:0000269|PubMed:8416368};\n" +
                "CC       Name=Gag polyprotein;\n" +
                "CC         IsoId=P03345-1; Sequence=External;\n" +
                "CC         Note=Produced by conventional translation.\n" +
                "CC         {ECO:0000269|PubMed:8416368};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(alternativeProductsLine);

        List<AlternativeProductsComment> alternativeProductsComments = entry.getCommentByType(CommentType.ALTERNATIVE_PRODUCTS);
        assertNotNull(entry);
        AlternativeProductsMap alternativeProductsMap = new AlternativeProductsMap(alternativeProductsComments);
        Map<String,String> mappedAlternativeProducts = alternativeProductsMap.attributeValues();
        assertNotNull(mappedAlternativeProducts);
        String value = mappedAlternativeProducts.get("cc:alternative_products");
        String expectedValue = "ALTERNATIVE PRODUCTS:  Event=Ribosomal frameshifting; Named isoforms=3; " +
                "Comment=This strategy of translation probably allows the virus to modulate the quantity of " +
                "each viral protein. {ECO:0000305}; Name=Gag-Pro-Pol polyprotein; IsoId=P03362-1; " +
                "Sequence=Displayed; Note=Produced by -1 ribosomal frameshiftings at the gag-pro and gag-pol " +
                "genes boundaries. {ECO:0000269|PubMed:8416368}; Name=Gag-Pro polyprotein; IsoId=P10274-1; " +
                "Sequence=External; Note=Produced by -1 ribosomal frameshifting at the gag-pro genes boundary. " +
                "{ECO:0000269|PubMed:8416368}; Name=Gag polyprotein; IsoId=P03345-1; Sequence=External; " +
                "Note=Produced by conventional translation. {ECO:0000269|PubMed:8416368};";
        assertEquals(expectedValue,value);
    }
}